package com.example.web_is.entity.mapper;


import com.example.jpa.mapper.BaseEntityMapper;
import com.example.web_is.data.User;
import com.example.web_is.entity.UserEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper extends BaseEntityMapper<User, UserEntity> {
}
