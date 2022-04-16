package com.example.web_is.repository;

import com.example.web_is.data.Article;
import com.example.web_is.data.User;

import java.util.List;

public interface ArticleRepository {
    Article get(Long id);
    List<Article> getAll();
    Article create(Article article);
    Article update(Article article);
    void delete(Long id);
}
