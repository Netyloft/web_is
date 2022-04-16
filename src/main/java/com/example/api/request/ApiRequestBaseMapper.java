package com.example.api.request;

import java.util.List;

public interface ApiRequestBaseMapper<R, D> {

    D toDomain(R request);

    List<D> toDomain(List<R> request);

}
