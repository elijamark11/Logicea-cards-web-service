package com.logicea.cards.helper;

public enum ResponseStatus {
    SUCCESS("000", "Request Successful"),
    ERROR("001", "Request failed"),
    INTERNAL_ERROR("1010", "Internal error, contact admin"),
    NOT_FOUND("2020", "Resource not found"),
    BAD_REQUEST("3030", "Bad request, something is missing in the request"),
    LOGIN_FAILURE("4040", "Wrong credentials"),
    UNAUTHORIZED("5050", "Unauthorized to access this resource"),
    INTEGRITY_VIOLATION("6060", "Cannot delete a parent row: a foreign key constraint fails"),
    DISABLED("7070", "Disabled"),

    OK("200", "Ok");

    private final String responseCode;
    private final String description;

    ResponseStatus(String responseCode, String description) {
        this.responseCode = responseCode;
        this.description = description;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getDescription() {
        return description;
    }
}