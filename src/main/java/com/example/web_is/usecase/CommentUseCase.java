package com.example.web_is.usecase;

import com.example.api.response.ApiResponse;
import com.example.api.response.ApiResponseListData;
import com.example.web_is.data.Comment;
import com.example.web_is.data.User;
import com.example.web_is.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface CommentUseCase {
    List<Comment> getComments(Long articleId);
    Comment createComment(Comment comment);
}
