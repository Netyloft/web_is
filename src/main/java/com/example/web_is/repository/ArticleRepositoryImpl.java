package com.example.web_is.repository;

import com.example.web_is.dao.ArticleDao;
import com.example.web_is.data.Article;
import com.example.web_is.entity.mapper.ArticleEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository{

    private final ArticleDao dao;
    private final ArticleEntityMapper mapper;

    @Override
    public Article get(Long id) {
        return mapper.toDto(dao.getById(id));
    }

    @Override
    public List<Article> getAll() {
        return mapper.toDto(dao.findAll());
    }

    @Override
    public Article create(Article article) {
        return mapper.toDto(dao.save(mapper.toEntity(article)));
    }

    @Override
    public Article update(Article article) {
        return mapper.toDto(dao.save(mapper.toEntity(article)));
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }
}
