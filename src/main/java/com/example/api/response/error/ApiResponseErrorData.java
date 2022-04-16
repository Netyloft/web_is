package com.example.api.response.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ApiResponseErrorData {

    private Integer code;
    private String message;
    private List<ApiResponseErrorItem> errors;

}
