package com.example.web_is.dao;

import com.example.jpa.dao.BaseCrudDao;
import com.example.web_is.entity.UserEntity;

import java.util.List;

public interface UserDao extends BaseCrudDao<UserEntity> {
    List<UserEntity> findByNickNameStartingWith(String nickName);
}
