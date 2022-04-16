package com.example.jpa.mapper;

import com.example.api.resolver.paging.Pageable;
import com.example.jpa.OffsetPagination;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface BaseEntityPageMapper<D, E extends Serializable> extends BaseEntityOutMapper<D, E> {

    default Pageable<D> toDto(Page<E> page, OffsetPagination pagination) {
        return new Pageable<D>(
            pagination.getOffset(),
            page.getTotalElements(),
            toDto(page.getContent())
        );
    }

    default Pageable<D> toDto(List<D> page, Long count, OffsetPagination pagination) {
        return new Pageable<D>(
            pagination.getOffset(),
            count,
            page
        );
    }

}
