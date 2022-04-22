package com.example.web_is.dao;

import com.example.jpa.dao.BaseCrudDao;
import com.example.web_is.entity.ArticleEntity;

import java.util.List;

public interface ArticleDao extends BaseCrudDao<ArticleEntity> {
    List<ArticleEntity> findByTagsContaining(String tags);
    List<ArticleEntity> findByTitleContaining(String title);
}
