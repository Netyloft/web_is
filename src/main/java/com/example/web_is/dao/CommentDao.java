package com.example.web_is.dao;

import com.example.jpa.dao.BaseCrudDao;
import com.example.web_is.entity.CommentEntity;
import com.example.web_is.entity.UserEntity;

import java.util.List;

public interface CommentDao extends BaseCrudDao<CommentEntity> {
    List<CommentEntity> getAllByArticleId(Long articleId);
}
