package com.example.jpa.mapper;

import com.example.api.resolver.paging.Pageable;
import com.example.jpa.OffsetPagination;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface BaseEntityMapper<D, E extends Serializable> extends BaseEntityInMapper<D, E>, BaseEntityPageMapper<D, E> {

    default Pageable<D> toDto(Page<E> page, OffsetPagination pagination) {
        return new Pageable<D>(
                pagination.getOffset(),
                page.getTotalElements(),
                toDto(page.getContent())
        );
    }

}
