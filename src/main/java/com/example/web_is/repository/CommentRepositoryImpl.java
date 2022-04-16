package com.example.web_is.repository;

import com.example.web_is.dao.CommentDao;
import com.example.web_is.dao.UserDao;
import com.example.web_is.data.Comment;
import com.example.web_is.entity.mapper.CommentEntityMapper;
import com.example.web_is.entity.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{

    private final CommentDao dao;
    private final CommentEntityMapper mapper;

    @Override
    public List<Comment> getComments(Long articleId) {
        return mapper.toDto(dao.getAllByArticleId(articleId));
    }

    @Override
    public Comment createComments(Comment comment) {
        return mapper.toDto(dao.save(mapper.toEntity(comment)));
    }
}
