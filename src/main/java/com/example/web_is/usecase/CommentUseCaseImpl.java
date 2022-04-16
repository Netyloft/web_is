package com.example.web_is.usecase;

import com.example.web_is.data.Comment;
import com.example.web_is.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CommentUseCaseImpl implements CommentUseCase{

    private final CommentRepository repository;

    @Override
    public List<Comment> getComments(Long articleId) {
        return repository.getComments(articleId);
    }

    @Override
    public Comment createComment(Comment comment) {
        return repository.createComments(comment);
    }


}
