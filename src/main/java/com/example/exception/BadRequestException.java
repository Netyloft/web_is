package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends WebRuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Object... args) {
        super(message, args);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(Throwable cause, String message) {
        super(cause, message);
    }

    public BadRequestException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
