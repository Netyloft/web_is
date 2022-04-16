package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends WebRuntimeException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Object... args) {
        super(message, args);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(Throwable cause, String message) {
        super(cause, message);
    }

    public UnauthorizedException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

}
