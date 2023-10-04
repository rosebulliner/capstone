package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public User getUsersById(Principal principal) {
        User user = null;
        user = userDao.getUserById(userDao.findIdByUsername(principal.getName()));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }else {
            return user;
        }

    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public List<User> getLsit() {
        return userDao.findAll();
    }



}
