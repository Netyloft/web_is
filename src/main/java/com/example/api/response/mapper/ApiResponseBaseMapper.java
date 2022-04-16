package com.example.api.response.mapper;



import com.example.api.response.ApiResponse;
import com.example.api.response.ApiResponseListData;

import java.util.List;

import static com.example.api.response.mapper.ApiResponseMapper.getXRequestId;


public interface ApiResponseBaseMapper<A, D> {

    A toApi(D model);

    List<A> toApi(List<D> list);

    default ApiResponse<A> toResponse(D item) {
        return new ApiResponse<>(
            getXRequestId(),
            toApi(item)
        );
    }

    default ApiResponse<ApiResponseListData<A>> toResponse(List<D> items) {
        return new ApiResponse<>(
            getXRequestId(),
            new ApiResponseListData<A>(toApi(items))
        );
    }

}
