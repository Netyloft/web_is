package com.example.jpa;

import com.example.api.resolver.filter.FilterParameters;
import org.springframework.lang.Nullable;

import java.util.List;

public interface BaseCrudRepository<T> extends BaseReadRepository<T, Long> {

    T create(T model);

    List<T> create(List<T> update);

    @Nullable
    T update(T model);

    List<T> update(List<T> update);

    void delete(long id);

    void delete(List<Long> ids);

    int delete(FilterParameters filters);

}
