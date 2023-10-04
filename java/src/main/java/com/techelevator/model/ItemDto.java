package com.techelevator.model;

public class ItemDto {
    private int listId;
    private int quantity;
    private String itemName;

    public ItemDto() {
    }

    public ItemDto(int listId, int quantity, String itemName) {
        this.listId = listId;
        this.quantity = quantity;
        this.itemName = itemName;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
