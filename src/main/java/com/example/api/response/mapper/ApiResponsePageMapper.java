//package com.example.api.response.mapper;
//
//import com.example.api.response.ApiResponse;
//
//
//import static com.example.api.response.mapper.ApiResponseMapper.getXRequestId;
//
//public abstract class ApiResponsePageMapper<M, D> implements ApiResponseBaseMapper<M, D> {
//
//    public Pageable<M> toApi(Pageable<D> pageable) {
//        return new Pageable<M>(
//            pageable.getStartIndex(),
//            pageable.getTotalItems(),
//            toApi(pageable.getItems())
//        );
//    }
//
//    public ApiResponse<ApiResponsePageData<M>> toResponse(Pageable<D> pageable) {
//        return new ApiResponse<>(
//            getXRequestId(),
//            new ApiResponsePageData<M>(toApi(pageable))
//        );
//    }
//
//
//}
