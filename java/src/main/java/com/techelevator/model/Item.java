package com.techelevator.model;

public class Item {
    private String itemName;
    private int id;
    private int quantity;
    private boolean isCompleted;


 public Item(){};

    public Item(String itemName, int id, int quantity) {
        this.itemName = itemName;
        this.id = id;
        this.quantity = quantity;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

