package com.techelevator.model;

import java.util.List;


public class Lists {
    private String listName;
    private int id;
    private int groupId;
    private List<Item> itemsInList;

    public Lists() {
    }

    ;

    public Lists(String listName, int id, int groupId) {
        this.listName = listName;
        this.id = id;
        this.groupId = groupId;
        }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItemsInList() {
        return itemsInList;
    }

    public void setItemsInList(List<Item> itemsInList) {
        this.itemsInList = itemsInList;
    }


}
