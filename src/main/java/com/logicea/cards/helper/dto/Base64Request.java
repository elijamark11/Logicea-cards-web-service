package com.logicea.cards.helper.dto;

public class Base64Request {
    private String base64EncodedString;

    public String getBase64EncodedString() {
        return base64EncodedString;
    }

    public Base64Request setBase64EncodedString(String base64EncodedString) {
        this.base64EncodedString = base64EncodedString;
        return this;
    }
}
