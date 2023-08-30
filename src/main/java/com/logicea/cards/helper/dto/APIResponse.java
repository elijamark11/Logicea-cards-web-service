package com.logicea.cards.helper.dto;

public class APIResponse <T> extends BaseApiResponse {

    private T data;

    public APIResponse(String status, String description, T data) {
        super(status, description);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
