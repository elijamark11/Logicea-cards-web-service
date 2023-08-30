package com.logicea.cards.helper.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseApiResponse {

    private String status;
    private String description;

    public BaseApiResponse(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
