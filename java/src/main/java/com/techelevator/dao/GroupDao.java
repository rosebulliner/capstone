package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.Item;

import java.util.List;

public interface GroupDao {

    Group getGroupById(int id);
    Group getGroupByName(String name);
    List<Group> listGroupsByUserId(int userId);
    Group add(Group group);
    Group update(Group group);

}
