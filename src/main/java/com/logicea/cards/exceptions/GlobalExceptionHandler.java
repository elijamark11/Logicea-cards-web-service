package com.logicea.cards.exceptions;


import com.logicea.cards.helper.ResponseStatus;
import com.logicea.cards.helper.dto.ApiErrorResponse;
import com.logicea.cards.helper.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ResourceNotFoundException.class,
            BadRequestException.class,
    })
    ResponseEntity<ApiErrorResponse> parseRequestErrors(Exception e) {

        ApiErrorResponse response = new ApiErrorResponse(ResponseStatus.ERROR.getResponseCode(), ResponseStatus.ERROR.getDescription());

        ArrayList<ErrorResponse> errors = new ArrayList<>();

        ResponseEntity.BodyBuilder builder;

        if (e instanceof ResourceNotFoundException) {

            ResourceNotFoundException ex = ((ResourceNotFoundException) e);

            errors.add(new ErrorResponse(ex.getResponseStatus().getResponseCode(), ex.getMessage()));

            builder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        } else if (e instanceof BadRequestException) {

            BadRequestException ex = ((BadRequestException) e);

            errors.add(new ErrorResponse(ex.getResponseStatus().getResponseCode(), ex.getMessage()));

            builder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        } else if (e instanceof RuntimeException) {
            var message = e.getMessage().trim();
            if (message.startsWith("com.rack.bo.helper.exception.BadRequestException")) {
                message = message.substring(message.indexOf(":") + 1);

                builder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
                errors.add(new ErrorResponse(ResponseStatus.BAD_REQUEST.getResponseCode(), message.trim()));
            } else {
                builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
                errors.add(new ErrorResponse(ResponseStatus.INTERNAL_ERROR.getResponseCode(), ResponseStatus.INTERNAL_ERROR.getDescription()));
            }
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

            errors.add(new ErrorResponse(ResponseStatus.INTERNAL_ERROR.getResponseCode(), ResponseStatus.INTERNAL_ERROR.getDescription()));
        }

        response.setError(errors);

        return builder.body(response);
    }
}