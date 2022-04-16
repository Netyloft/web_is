package com.example.api.response.error;

import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.example.api.Constants.X_REQUEST_ID;


@Data
public class ApiResponseError {

    private String id;
    private ApiResponseErrorData error;

    public ApiResponseError(ApiResponseErrorData error) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        this.id = request.getHeader(X_REQUEST_ID);
        this.error = error;
    }

}
