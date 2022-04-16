package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotImplemented extends WebRuntimeException {

    public NotImplemented(String message) {
        super(message);
    }

    public NotImplemented(String message, Object... args) {
        super(message, args);
    }

    public NotImplemented(Throwable cause) {
        super(cause);
    }

    public NotImplemented(Throwable cause, String message) {
        super(cause, message);
    }

    public NotImplemented(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
