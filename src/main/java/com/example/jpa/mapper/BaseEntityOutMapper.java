package com.example.jpa.mapper;

import java.io.Serializable;
import java.util.List;

public interface BaseEntityOutMapper<D, E extends Serializable> {

    D toDto(E entity);

    List<D> toDto(List<E> entityList);

}
