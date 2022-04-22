package com.example.web_is.usecase;

import com.example.web_is.data.Article;
import com.example.web_is.filter.ArticleFilter;

import java.util.List;

public interface ArticleUseCase {
    List<Article> getArticles();
    List<Article> getArticles(ArticleFilter filter);
    Article getArticle(Long id);
    Article createArticle(Article article);
    Article updateArticle(Article article);
    void deleteArticle(Long id);
}
