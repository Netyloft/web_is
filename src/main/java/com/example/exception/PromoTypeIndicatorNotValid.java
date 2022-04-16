package com.example.exception;

public class PromoTypeIndicatorNotValid extends BadRequestException {

    public PromoTypeIndicatorNotValid(String message) {
        super(message);
    }

}
