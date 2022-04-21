package com.example.web_is.controller;

import com.example.api.response.ApiResponse;
import com.example.api.response.ApiResponseListData;
import com.example.web_is.data.Comment;
import com.example.web_is.request.CommentRequest;
import com.example.web_is.request.mapper.CommentRequestMapper;
import com.example.web_is.response.CommentResponse;
import com.example.web_is.response.mapper.CommentResponseMapper;
import com.example.web_is.usecase.CommentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.example.web_is.controller.Constants.COMMENT_API_BASE_PATH;

@Tag(name = "Комментарий")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = COMMENT_API_BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentResponseMapper responseMapper;
    private final CommentRequestMapper requestMapper;
    private final CommentUseCase useCase;

    @Operation(summary = "Получить список коментариев")
    @GetMapping(value = "/{articleId}")
    public ApiResponse<ApiResponseListData<CommentResponse>> getComments(@PathVariable Long articleId){
        return responseMapper.toResponse(useCase.getComments(articleId));
    }

    @Operation(summary = "Создать коментарий")
    @PostMapping(value = "/create")
    public ApiResponse<CommentResponse> createComments(@RequestBody CommentRequest request) {
        Comment comment = useCase.createComment(requestMapper.toDomain(request));
        comment.setId(null);
        return responseMapper.toResponse(comment);
    }
}
