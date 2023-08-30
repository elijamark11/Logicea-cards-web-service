package com.logicea.cards.helper.dto;

import java.util.ArrayList;

public class ApiErrorResponse extends BaseApiResponse {

    private ArrayList<ErrorResponse> error;

    public ApiErrorResponse(String status, String description) {
        super(status, description);

    }

    public ApiErrorResponse(String status, String description, ArrayList<ErrorResponse> errors) {
        super(status, description);
        this.error = errors;
    }

    public ArrayList<ErrorResponse> getError() {
        return error;
    }

    public void setError(ArrayList<ErrorResponse> error) {
        this.error = error;
    }
}
