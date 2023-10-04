package com.techelevator.dao;

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
public class JdbcListDao implements ListsDao {

    private final JdbcTemplate db;

    public JdbcListDao(JdbcTemplate db) {
        this.db = db;
    }


    @Override
    public Lists getListsById(int id) {
        Lists list = null;
        String sql = " SELECT list_id, group_id, list_name from list WHERE list_id = ? ";
        SqlRowSet rs = db.queryForRowSet(sql, id);
        if (rs.next()) {
            list = mapList(rs);

        }
        return list;
    }

    @Override
    public List<Lists> getAll() {
        List<Lists> listList = new ArrayList<>();
        try {
            String sql = " SELECT * FROM list";
            SqlRowSet rs = db.queryForRowSet(sql);
            while (rs.next()) {
                listList.add(mapList(rs));
            }
            for (Lists list: listList){
                list.setItemsInList(listItemsById(list.getId()));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return listList;
    }

//    @Override
//    public Lists getListsByName(String name) {
//        name = "%" + name + "%";
//        String sql = "SELECT * FROM list WHERE list_name ILIKE ? ;";
//        Lists list = new Lists();
//        try {
//            SqlRowSet rs = db.queryForRowSet(sql, name);
//            if (rs.next()) {
//                list = mapList(rs);
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (BadSqlGrammarException e) {
//            throw new DaoException("SQL syntax error", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//        return list;
//    }

    // db may need to be reformatted to get this to work as intended, atm im thinking a join table for list_users, and clarify what user_id on list stands for . likely creator or maybe who grabbed the list
    @Override
    public List<Lists> listListsByUserId(int userId) {
        List<Lists> userLists = new ArrayList<>();
        try {
            String sql = "select * FROM list join group_user on group_user.group_id=list.group_id join users on users.user_id=group_user.user_id where users.user_id = ?";
            SqlRowSet rs = db.queryForRowSet(sql, userId);
            while (rs.next()) {

                userLists.add(mapList(rs));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return userLists;
    }

    //not fxal rewrite db in mock db
    // created a list of lists that contain an array list
    @Override
    public List<Lists> listListsByGroupId(int groupId) {
        List<Lists> groupListList = new ArrayList<>();
        String sql = "SELECT * from list WHERE group_id = ?";
        SqlRowSet rs = db.queryForRowSet(sql, groupId);
        while (rs.next()) {
            Lists data = mapList(rs);
            List<Item> itemList = listItemsById(data.getId());
            data.setItemsInList(itemList);
            groupListList.add(data);
        }
        return groupListList;
    }

    @Override
    public List<Item> listItemsById(int listId) {
        List<Item> itemListByListId = new ArrayList<>();
        String sql = "SELECT item_id, item_name, quantity, is_completed from item " +
                "JOIN list_item USING (item_id) JOIN list USING(list_id) WHERE list_id = ?";
        SqlRowSet rs = db.queryForRowSet(sql, listId);
        while (rs.next()) {
            itemListByListId.add(mapItem(rs));
        }
        return itemListByListId;
    }

    public Lists add(Lists lists) {
        String sql = "INSERT INTO list (list_name) VALUES (?) RETURNING list_id";
        try {
            Integer list_id = db.queryForObject(sql, Integer.class, lists.getListName());
            lists.setId(list_id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return lists;
    }

    @Override
    public Lists update(Lists lists) {
        Lists updatedList = new Lists();
        String sql = "UPDATE list SET list_name = ?, group_id = ? WHERE list_id = ?;";
        try {
            int rowsAffected = db.update(sql, lists.getGroupId(), lists.getListName(), lists.getId());
            updatedList = getListsById(lists.getId());
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
        return updatedList;
    }

    //delete list in list as well list_item
    @Override
    public int delete(int listId) {
        String sql = "DELETE FROM list WHERE list_id =?;";
        int rowsDeleted = 0;
        try {
            rowsDeleted = db.update(sql, listId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsDeleted;
    }

    private Lists mapList(SqlRowSet rs) {
        Lists lists = new Lists();
        lists.setListName(rs.getString("list_name"));
        lists.setId(rs.getInt("list_id"));
        lists.setGroupId(rs.getInt("group_id"));
        return lists;
    }

    private Item mapItem(SqlRowSet rs) {
        Item item = new Item();
        item.setItemName(rs.getString("item_name"));
        item.setId(rs.getInt("item_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setCompleted(rs.getBoolean("is_completed"));
        return item;
    }

}

//    String sql = "";
//    SqlRowSet rs = db.queryForRowSet(sql, ?);
//       if (rs.next()) {
//        return map?(rs);
//    } else return null;