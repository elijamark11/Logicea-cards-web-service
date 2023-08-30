package com.logicea.cards.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LogInResponse {
    public Long userId;
    public UserGroupDetails userGroup;
    private String accessToken;
    private String name;
    private String email;
    private int expiresIn;
    private String phoneNumber;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGroupDetails {
        private Long id;
        private String name;
        private String description;
    }
}