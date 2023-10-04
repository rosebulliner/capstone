package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> listUsersByGroupId(int groupID);

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);



    boolean create(String firstName, String lastName, String username, String email, String password, String role);
}
