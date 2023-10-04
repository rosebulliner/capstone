package com.techelevator.controller;

import com.techelevator.dao.ListsDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Item;
import com.techelevator.model.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
quick ref:
endpoint methods have @RequestMapping
return from DAO not jdbc
GET: @requestMapping(path, method - RequestMethod.GET)
    **@RequestParam(required= false) (specific params) (removed req if needed)


*/
@RestController
@CrossOrigin
// allows you to communicate between two different origins (you would want to set port specifically, ideally)
@RequestMapping(path = "/list")
public class ListController {

    @Autowired
    private ListsDao dao;
    @Autowired
    private UserDao userDao;

//    public ListController(ListsDao dao, UserDao userDao) {
//        this.dao = dao;
//        this.userDao = userDao;
//    }

    public ListController() {
    }

    public ListController(ListsDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Lists> getAll() {
        return dao.getAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Lists getListById(@PathVariable int id) {
        return dao.getListsById(id);
    }

    @RequestMapping(path = "/userList/{userID}", method = RequestMethod.GET)
    public List<Lists> getListsByUserId(Principal principal, @PathVariable int userID) {
        return dao.listListsByUserId(userDao.findIdByUsername(principal.getName()));
        // return dao.listListsByUserId(userID);
    }

    //not currently fx working on update in mockdb
    @RequestMapping(path = "/group/{groupID}", method = RequestMethod.GET)
    public List<Lists> getListsByGroupId(@PathVariable int groupID) {
        return dao.listListsByGroupId(groupID);
    }

    @RequestMapping(path = "/items/{id}", method = RequestMethod.GET)
    public List<Item> getItemsListById(@PathVariable int id) {
        return dao.listItemsById(id);
    }

//    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
//    public Lists getListByName(@PathVariable String name) {
//        return dao.getListsByName(name);
//    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public Lists newLists(@RequestBody Lists lists) {
        return dao.add(lists);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public Lists update(@RequestBody Lists lists) {
        return dao.update(lists);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public int delete(@PathVariable int id) {
        return dao.delete(id);
    }

}