package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends WebRuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Object... args) {
        super(message, args);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }

    public ConflictException(Throwable cause, String message) {
        super(cause, message);
    }

    public ConflictException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
