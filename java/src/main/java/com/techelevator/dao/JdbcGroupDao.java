package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.Item;
import com.techelevator.model.Lists;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGroupDao implements GroupDao {

    private final JdbcTemplate db;

    public JdbcGroupDao(JdbcTemplate db) {
        this.db = db;
    }


    @Override
    public Group getGroupById(int id) {
        String sql = "SELECT * FROM diff_groups WHERE group_id = ?";
        SqlRowSet rs = db.queryForRowSet(sql, id);
        if (rs.next()) {
            return mapGroup(rs);

        } return null;    }

    @Override
    public Group getGroupByName(String name) {
        name = "%"+name+"%";
        String sql = "SELECT * FROM diff_groups WHERE group_name ILIKE ?; ";
        SqlRowSet rs = db.queryForRowSet(sql, name);
        if (rs.next()) {
            return mapGroup(rs);
        }
        return null;
    }

    @Override
    public List<Group> listGroupsByUserId(int userId) {
        List <Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM diff_groups join group_user on diff_groups.group_id =  group_user.group_id join users on users.user_id=group_user.user_id WHERE users.user_id = ?";
        SqlRowSet rs = db.queryForRowSet(sql, userId);
        while (rs.next()) {
              groups.add(mapGroup(rs));
        }
        return groups;
    }
//create group insert into list also
    @Override
    public Group add(Group group) {

        String sql = "INSERT INTO diff_groups (group_name) VALUES (?) RETURNING group_id;";
        int groupId = db.queryForObject(sql, int.class, group.getName());

        return group;
    }

    @Override
    public Group update(Group group) {
        Group updatedGroup = new Group();
        String sql = "UPDATE diff_groups SET group_name = ? WHERE group_id = ?;";
        try {
            int rowsAffected = db.update(sql, group.getName(), group.getId());
            updatedGroup = getGroupById(group.getId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero Rows Affected, Expected One or More");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedGroup;
    }


    public int delete(int groupId) {
        String sql = "DELETE FROM diff_groups where group_id = ?";
        String sqls = "DELETE FROM list group_id = ?";
        int rowsDeleted = 0;
        return rowsDeleted;
    }

    private Group mapGroup(SqlRowSet rs){
        Group group = new Group();
        group.setName(rs.getString("group_name"));
        group.setId(rs.getInt("group_id"));
        return group;
    }
    }



