package com.example.web_is.entity.mapper;

import com.example.api.response.mapper.ApiResponseBaseMapper;
import com.example.jpa.mapper.BaseEntityMapper;
import com.example.web_is.data.Comment;
import com.example.web_is.data.User;
import com.example.web_is.entity.CommentEntity;
import com.example.web_is.entity.UserEntity;
import com.example.web_is.response.CommentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentEntityMapper extends BaseEntityMapper<Comment, CommentEntity> {
}
