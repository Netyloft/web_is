package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends WebRuntimeException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Object... args) {
        super(message, args);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    public ForbiddenException(Throwable cause, String message) {
        super(cause, message);
    }

    public ForbiddenException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

}
