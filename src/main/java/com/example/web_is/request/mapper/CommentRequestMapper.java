package com.example.web_is.request.mapper;

import com.example.api.request.ApiRequestBaseMapper;
import com.example.web_is.data.Comment;
import com.example.web_is.data.User;
import com.example.web_is.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class CommentRequestMapper implements ApiRequestBaseMapper<CommentRequest, Comment> {

    @AfterMapping
    public void toDomain(@MappingTarget Comment model, CommentRequest request) {
        User user = new User();
        user.setId(request.getAuthorId());
        model.setAuthor(user);
    }
}
