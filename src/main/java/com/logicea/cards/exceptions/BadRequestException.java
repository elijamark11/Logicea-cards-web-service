package com.logicea.cards.exceptions;


import com.logicea.cards.helper.ResponseStatus;

public class BadRequestException extends Exception {

    public ResponseStatus responseStatus = ResponseStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, ResponseStatus status) {
        super(message);
        this.responseStatus = status;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}