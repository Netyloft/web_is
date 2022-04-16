package com.example.jpa.mapper;

import java.util.List;

public interface BaseEntityInMapper<D, E> {

    E toEntity(D dto);

    List<E> toEntity(List<D> dtoList);

}
