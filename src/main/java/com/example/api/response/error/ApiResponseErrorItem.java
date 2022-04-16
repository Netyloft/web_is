package com.example.api.response.error;

import lombok.Data;

@Data
public class ApiResponseErrorItem {

    private String reason;
    private String message;
    private String location;

    public ApiResponseErrorItem(String reason, String message, String location) {
        this.reason = reason;
        this.message = message;
        this.location = location;
    }

}
