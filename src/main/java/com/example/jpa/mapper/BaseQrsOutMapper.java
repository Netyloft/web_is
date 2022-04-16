package com.example.jpa.mapper;

import java.util.List;

public interface BaseQrsOutMapper<D, E> {

    D toDto(E qrs);

    List<D> toDto(List<E> qrsList);

}
