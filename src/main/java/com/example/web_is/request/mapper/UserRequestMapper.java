package com.example.web_is.request.mapper;

import com.example.api.request.ApiRequestBaseMapper;
import com.example.web_is.data.Comment;
import com.example.web_is.data.User;
import com.example.web_is.request.CommentRequest;
import com.example.web_is.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class UserRequestMapper implements ApiRequestBaseMapper<UserRequest, User> {
}
