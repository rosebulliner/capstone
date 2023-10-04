package com.techelevator.controller;

import com.techelevator.dao.ItemDao;
import com.techelevator.dao.ListsDao;
import com.techelevator.model.Item;
import com.techelevator.model.ItemDto;
import com.techelevator.model.ListItemDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(path = "/item")
@RestController
public class ItemController {
    private ItemDao itemDao;

    public ItemController(ItemDao itemDao, ListsDao listsDao) {
        this.itemDao = itemDao;

    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Item getItems(@PathVariable int id) {
        return itemDao.getItemById(id);
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ItemDto add(@RequestBody ItemDto item) {

        return itemDao.add(item);
    }

    //test update endpoints to
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public Item update(@RequestBody Item item) {
        return itemDao.update(item);
    }


    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public int delete(@PathVariable int id) {
        return itemDao.delete(id);
    }

    @RequestMapping(path = "/toggle/{itemId}", method = RequestMethod.PUT)
    public ListItemDto toggle(@PathVariable int itemId) {
        return itemDao.toggleCompletion(itemId);
    }

    @RequestMapping(path = "details/{id}", method = RequestMethod.GET)
    public ListItemDto getItemDetails(@PathVariable int id) {
        return itemDao.getListItemDetails(id);
    }
}

