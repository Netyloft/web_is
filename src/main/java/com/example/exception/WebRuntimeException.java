package com.example.exception;

public class WebRuntimeException extends RuntimeException {

    public WebRuntimeException(String message) {
        super(message);
    }

    public WebRuntimeException(String message, Object... args) {
        super(String.format(message, args));
    }

    public WebRuntimeException(Throwable cause) {
        super(cause);
    }

    public WebRuntimeException(Throwable cause, String message) {
        super(message, cause);
    }

    public WebRuntimeException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

}
