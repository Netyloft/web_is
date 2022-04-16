package com.example.web_is.response.mapper;

import com.example.api.response.mapper.ApiResponseBaseMapper;
import com.example.web_is.data.User;
import com.example.web_is.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends ApiResponseBaseMapper<UserResponse, User> {
}
