package com.techelevator.dao;

import com.techelevator.model.Item;
import com.techelevator.model.ItemDto;
import com.techelevator.model.ListItemDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcItemDao implements ItemDao {

    private final JdbcTemplate db;

    public JdbcItemDao(JdbcTemplate db) {
        this.db = db;
    }


    @Override
    public Item getItemById(int id) {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        SqlRowSet rs = db.queryForRowSet(sql, id);
        if (rs.next()) {
            return mapItem(rs);
        } else return null;
    }


    @Override
    public ItemDto add(ItemDto item) {

        String sql = "INSERT INTO item(item_name, quantity) VALUES (?,?) RETURNING item_id;";

        try {
            Integer id = (db.queryForObject(sql, Integer.class, item.getItemName(), item.getQuantity()));
            sql = "INSERT INTO list_item(item_id, list_id, is_completed) VALUES (?,?,false);";
            db.update(sql, id, item.getListId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return item;
    }

    @Override
    public ListItemDto toggleCompletion(int id) {

        String sql = "UPDATE list_item SET is_completed = ? WHERE item_id = ?";
        ListItemDto itemStatus = getListItemDetails(id);
        try {
            int rowsAffected = db.update(sql, !itemStatus.isCompleted(), id);
            itemStatus.setCompleted(!itemStatus.isCompleted());
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
        return itemStatus;
    }

    @Override
    public ListItemDto getListItemDetails(int id) {
        String sql = "SELECT * FROM list_item WHERE item_id=?";
        ListItemDto itemDetails = new ListItemDto();
        SqlRowSet rs = db.queryForRowSet(sql, id);
        if (rs.next()) {
            itemDetails.setCompleted(rs.getBoolean("is_completed"));
            itemDetails.setListId(rs.getInt("list_id"));
            itemDetails.setItemId(id);
        }
        return itemDetails;
    }

    @Override
    public Item update(Item item) {
        Item updatedItem = new Item();
        String sql = "UPDATE item SET item_name = ?, quantity = ? WHERE item_id = ?;";
        try {
            int rowsAffected = db.update(sql, item.getItemName(), item.getQuantity(), item.getId());
            updatedItem = getItemById(item.getId());
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
        return updatedItem;
    }

    //https://lms.techelevator.com/cohorts/407/blocks/14/content_files/02_Data_Access_Part_2/05-delete.md
    @Override
    public int delete(int itemID) {
        String sql1 = "DELETE FROM list_item WHERE item_id = ?;";
        String sql = "DELETE FROM item WHERE item_id =?;";
        int rowsDeleted = 0;
        try {
            db.update(sql1, itemID);
            rowsDeleted = db.update(sql, itemID);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsDeleted;
    }


    private Item mapItem(SqlRowSet rs) {
        Item item = new Item();
        item.setItemName(rs.getString("item_name"));
        item.setId(rs.getInt("item_id"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }


}
