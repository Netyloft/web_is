package com.example.web_is.request.mapper;

import com.example.api.request.ApiRequestBaseMapper;
import com.example.web_is.data.Article;
import com.example.web_is.data.Comment;
import com.example.web_is.data.User;
import com.example.web_is.request.ArticleRequest;
import com.example.web_is.request.CommentRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ArticleRequestMapper implements ApiRequestBaseMapper<ArticleRequest, Article> {

    @AfterMapping
    public void toDomain(@MappingTarget Article model, ArticleRequest request) {
        User user = new User();
        user.setId(request.getAuthorId());
        model.setAuthor(user);
    }
}
