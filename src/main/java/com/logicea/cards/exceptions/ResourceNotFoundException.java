package com.logicea.cards.exceptions;


import com.logicea.cards.helper.ResponseStatus;

public class ResourceNotFoundException extends Exception {

    public ResponseStatus responseStatus = ResponseStatus.NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, ResponseStatus status) {
        super(message);
        this.responseStatus = status;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}