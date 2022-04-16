package com.example.web_is.entity.mapper;

import com.example.api.response.mapper.ApiResponseBaseMapper;
import com.example.jpa.mapper.BaseEntityMapper;
import com.example.web_is.data.Article;
import com.example.web_is.data.User;
import com.example.web_is.entity.ArticleEntity;
import com.example.web_is.entity.UserEntity;
import com.example.web_is.response.ArticleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleEntityMapper extends BaseEntityMapper<Article, ArticleEntity> {

    @Mapping(target = "comments", ignore = true)
    Article toDto(ArticleEntity entity);
}
