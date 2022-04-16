package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends WebRuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... args) {
        super(message, args);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(Throwable cause, String message) {
        super(cause, message);
    }

    public NotFoundException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

}
