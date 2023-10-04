package com.techelevator.controller;


import com.techelevator.dao.GroupDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Group;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RequestMapping(path = "/group")
@RestController

public class GroupController {

    private GroupDao groupDao;
    private UserDao userDao;

    public GroupController(GroupDao groupDao, UserDao userDao) {
        this.groupDao = groupDao;
        this.userDao = userDao;
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Group getGroupId(@PathVariable int id) {
        Group group = groupDao.getGroupById(id);

        if (group == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            return group;
        }

    }

    @RequestMapping(path = "/lists", method = RequestMethod.GET)
    public List<Group> listOfGroups(Principal principal) {
        return groupDao.listGroupsByUserId(userDao.findIdByUsername(principal.getName()));
    }

    @RequestMapping(path = "/newGroup", method = RequestMethod.POST)
    public Group newGroups(@RequestBody Group group) {
        return groupDao.add(group);
    }

    @RequestMapping(path = "/updateGroup", method = RequestMethod.PUT)
    public Group updateGroup(@RequestBody Group group) {
        return groupDao.update(group);
    }

}
