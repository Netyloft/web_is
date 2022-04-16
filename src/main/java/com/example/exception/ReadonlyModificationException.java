package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReadonlyModificationException extends WebRuntimeException {

    public ReadonlyModificationException(String message) {
        super(message);
    }

    public ReadonlyModificationException(String message, Object... args) {
        super(message, args);
    }

    public ReadonlyModificationException(Throwable cause) {
        super(cause);
    }

    public ReadonlyModificationException(Throwable cause, String message) {
        super(cause, message);
    }

    public ReadonlyModificationException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

}
