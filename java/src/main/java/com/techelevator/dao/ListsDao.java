package com.techelevator.dao;

import com.techelevator.model.Item;
import com.techelevator.model.Lists;

import java.util.List;

public interface ListsDao {

    Lists getListsById(int id);

    List<Lists> getAll();

    // Lists getListsByName(String name);
    List<Lists> listListsByUserId(int userId);

    List<Lists> listListsByGroupId(int groupId);

    List<Item> listItemsById(int listId);

    Lists add(Lists lists);

    Lists update(Lists lists);

    int delete(int listId);


}
