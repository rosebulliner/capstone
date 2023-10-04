package com.techelevator.dao;

import com.techelevator.model.Item;
import com.techelevator.model.ItemDto;
import com.techelevator.model.ListItemDto;

public interface ItemDao {

    Item getItemById(int id);

    ItemDto add(ItemDto item);

    ListItemDto toggleCompletion(int id);

    ListItemDto getListItemDetails(int id);

    Item update(Item item);

    int delete(int itemId);
}
