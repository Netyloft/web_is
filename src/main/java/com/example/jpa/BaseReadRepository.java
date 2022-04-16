package com.example.jpa;

import com.example.api.resolver.filter.FilterParameters;
import com.example.api.resolver.paging.Pageable;
import com.example.api.resolver.paging.PagingParameters;
import org.springframework.lang.Nullable;

import java.util.List;

public interface BaseReadRepository<T, ID> {

    @Nullable
    T get(ID id);

    List<T> findAll(FilterParameters filters);

    Pageable<T> findAll(PagingParameters parameters);

    Pageable<T> findAll(FilterParameters filters, PagingParameters paging);

}
