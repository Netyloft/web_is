package com.example.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseReadDao<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

}
