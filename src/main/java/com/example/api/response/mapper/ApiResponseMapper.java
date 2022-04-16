package com.example.api.response.mapper;

import com.example.api.response.ApiResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.example.api.Constants.X_REQUEST_ID;


public class ApiResponseMapper {

    private ApiResponseMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getXRequestId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            return servletRequest.getHeader(X_REQUEST_ID);
        }
        return null;
    }


    public static <T> ApiResponse<T> singleFrom(T item) {
        return new ApiResponse<>(
            getXRequestId(),
            item
        );
    }

//    public static <T> ApiResponse<ApiResponsePageData<T>> pageFrom(Pageable<T> page) {
//        return new ApiResponse<>(
//            getXRequestId(),
//            new ApiResponsePageData<T>(page)
//        );
//    }

}
