package com.example.web_is.usecase;

import com.example.web_is.data.Article;

import java.util.List;

public interface ArticleUseCase {
    List<Article> getArticles();
    Article getArticle(Long id);
    Article createArticle(Article article);
    Article updateArticle(Article article);
    void deleteArticle(Long id);
}
