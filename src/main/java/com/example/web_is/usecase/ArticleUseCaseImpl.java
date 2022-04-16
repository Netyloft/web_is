package com.example.web_is.usecase;

import com.example.exception.BadRequestException;
import com.example.web_is.data.Article;
import com.example.web_is.data.Comment;
import com.example.web_is.repository.ArticleRepository;
import com.example.web_is.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class ArticleUseCaseImpl implements ArticleUseCase {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<Article> getArticles() {
        return articleRepository.getAll();
    }

    @Override
    public Article getArticle(Long id) {
        Article article = articleRepository.get(id);

        if(article == null){
            throw new BadRequestException("Статья с id: %s не найдена".formatted(id));
        }

        List<Comment> comments = commentRepository.getComments(id);
        article.setComments(comments);//todo это лучше мапить
        return article;
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.create(article);
    }

    @Override
    public Article updateArticle(Article article) {
        return articleRepository.update(article);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.delete(id);
    }
}