package com.example.web_is.response.mapper;

import com.example.api.response.mapper.ApiResponseBaseMapper;
import com.example.web_is.data.Article;
import com.example.web_is.data.Comment;
import com.example.web_is.response.ArticleResponse;
import com.example.web_is.response.CommentResponse;
import com.example.web_is.response.UserResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ArticleResponseMapper implements ApiResponseBaseMapper<ArticleResponse, Article> {
    @AfterMapping
    public void toResponse(@MappingTarget ArticleResponse response, Article model) {
        UserResponse user = new UserResponse();
        user.setNickName(model.getAuthor().getNickName());
        user.setId(model.getAuthor().getId());
        response.setAuthor(user);
    }
}
