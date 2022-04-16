package com.example.web_is.response.mapper;

import com.example.api.response.mapper.ApiResponseBaseMapper;
import com.example.web_is.data.Comment;
import com.example.web_is.response.CommentResponse;
import com.example.web_is.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class CommentResponseMapper implements ApiResponseBaseMapper<CommentResponse, Comment> {

    @AfterMapping
    public void toResponse(@MappingTarget CommentResponse response, Comment model) {
        UserResponse user = new UserResponse();
        user.setNickName(model.getAuthor().getNickName());
        user.setId(model.getAuthor().getId());
        response.setAuthor(user);
    }
}
