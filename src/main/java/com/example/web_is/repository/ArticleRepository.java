package com.example.web_is.repository;

import com.example.web_is.data.Article;
import com.example.web_is.data.User;
import com.example.web_is.filter.ArticleFilter;

import java.util.List;

public interface ArticleRepository {
    Article get(Long id);
    List<Article> getAll();
    List<Article> getAll(ArticleFilter filter);
    Article create(Article article);
    Article update(Article article);
    void delete(Long id);
}
