package com.example.web_is.repository;

import com.example.web_is.data.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getComments(Long articleId);

    Comment createComments(Comment comment);
}
