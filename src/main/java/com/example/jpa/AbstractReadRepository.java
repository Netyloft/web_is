package com.example.jpa;

import com.example.api.resolver.filter.FilterParameters;
import com.example.api.resolver.paging.Pageable;
import com.example.api.resolver.paging.PagingParameters;
import com.example.exception.NotFoundException;
import com.example.jpa.dao.BaseReadDao;
import com.example.jpa.mapper.BaseEntityPageMapper;
import com.example.jpa.utils.PaginationHelper;
import com.example.jpa.utils.SpecificationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractReadRepository<
    D,
    ID,
    E extends Serializable,
    Dao extends BaseReadDao<E, ID>,
    M extends BaseEntityPageMapper<D, E>
    > implements BaseReadRepository<D, ID> {

    protected final Dao dao;
    protected final M mapper;

    @Override
    public D get(ID id) {
        E entity = dao.findById(id).orElse(null);

        if (entity == null) {
            throw new NotFoundException("Запись не найдена");
        }

        return mapper.toDto(entity);
    }

    @Override
    public List<D> findAll(FilterParameters filters) {
        Specification<E> specification = SpecificationHelper.toSpecification(filters);
        List<E> entities = dao.findAll(specification);
        return mapper.toDto(entities);
    }

    @Override
    public Pageable<D> findAll(PagingParameters parameters) {
        OffsetPagination pagination = PaginationHelper.toPagination(parameters);
        Page<E> page = dao.findAll(pagination);
        return mapper.toDto(page, pagination);
    }

    @Override
    public Pageable<D> findAll(FilterParameters filters, PagingParameters paging) {
        Specification<E> specification = SpecificationHelper.toSpecification(filters);
        OffsetPagination pagination = PaginationHelper.toPagination(paging);
        Page<E> recordEntityPage = dao.findAll(specification, pagination);
        return mapper.toDto(recordEntityPage, pagination);
    }

}
