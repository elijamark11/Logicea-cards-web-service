package com.logicea.cards.dtos.cards;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private Long id;
    private String name;
    private String description;
    private String color;
    private String status;

    @ApiModelProperty(hidden = true)
    private CreatedByUser createdByUser;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedByUser {
        private String name;
        private String email;
    }
}