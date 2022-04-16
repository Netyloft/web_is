package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistException extends WebRuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, Object... args) {
        super(String.format(message, args));
    }

    public AlreadyExistException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistException(Throwable cause, String message) {
        super(cause, message);
    }

    public AlreadyExistException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

}
