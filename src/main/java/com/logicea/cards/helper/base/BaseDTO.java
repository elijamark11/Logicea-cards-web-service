package com.logicea.cards.helper.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseDTO {
    @ApiModelProperty(hidden = true)
    private Date createdOn;
}