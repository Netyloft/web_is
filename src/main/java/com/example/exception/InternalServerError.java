package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends WebRuntimeException {

    public InternalServerError(String message) {
        super(message);
    }

    public InternalServerError(String message, Object... args) {
        super(String.format(message, args));
    }

    public InternalServerError(Throwable cause) {
        super(cause);
    }

    public InternalServerError(Throwable cause, String message) {
        super(cause, message);
    }

    public InternalServerError(Throwable cause, String message, Object... args) {
        super(cause, String.format(message, args));
    }

}
