package com.example.exception;

import lombok.Getter;

import java.util.List;

public class PromoActivityValidationException extends BadRequestException{

    @Getter
    private final List<String> errors;

    public PromoActivityValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
