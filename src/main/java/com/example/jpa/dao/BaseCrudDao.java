package com.example.jpa.dao;

import com.example.jpa.entity.BaseEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseCrudDao<E extends BaseEntity> extends BaseReadDao<E, Long> {

    @Modifying
    void deleteByIdIn(List<Long> ids);
}
