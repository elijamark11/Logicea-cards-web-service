package com.logicea.cards.entities;

public enum CardStatus {
    TO_DO("To Do"),

    IN_PROGRESS("In Progress"),

    DONE("Done");

    private String status;

    CardStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}