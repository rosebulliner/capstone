package com.techelevator.model;

public class ListItemDto {
    private int itemId;
    private int listId;
    private boolean isCompleted;

    public ListItemDto() {
    }

    public ListItemDto(int itemId, int listId, boolean isCompleted) {
        this.itemId = itemId;
        this.listId = listId;
        this.isCompleted = isCompleted;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
