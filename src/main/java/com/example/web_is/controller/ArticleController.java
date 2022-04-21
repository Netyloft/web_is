package com.example.web_is.controller;

import com.example.api.response.ApiResponse;
import com.example.api.response.ApiResponseListData;
import com.example.web_is.data.Article;
import com.example.web_is.request.ArticleRequest;
import com.example.web_is.request.mapper.ArticleRequestMapper;
import com.example.web_is.response.ArticleResponse;
import com.example.web_is.response.mapper.ArticleResponseMapper;
import com.example.web_is.usecase.ArticleUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.example.web_is.controller.Constants.ARTICLE_API_BASE_PATH;


@Tag(name = "Стататья")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ARTICLE_API_BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    private final ArticleUseCase useCase;
    private final ArticleResponseMapper responseMapper;
    private final ArticleRequestMapper requestMapper;

    @Operation(summary = "Получить список статей")
    @GetMapping(value = "/")
    public ApiResponse<ApiResponseListData<ArticleResponse>> getArticles() {
        return responseMapper.toResponse(useCase.getArticles());
    }

    @Operation(summary = "Получить статью")
    @GetMapping(value = "/{id}")
    public ApiResponse<ArticleResponse> getArticle(@PathVariable Long id) {
        Article article = useCase.getArticle(id);
        return responseMapper.toResponse(article);
    }

    @Operation(summary = "Создать статьтю")
    @PostMapping(value = "/create")
    public ApiResponse<ArticleResponse> createArticle(@RequestBody ArticleRequest request) {
        Article article = useCase.createArticle(requestMapper.toDomain(request));
        article.setId(null);
        return responseMapper.toResponse(article);
    }

    @Operation(summary = "Обновить статью")
    @PutMapping(value = "/update")
    public ApiResponse<ArticleResponse> updateArticle(@RequestBody ArticleRequest request) {
        Article article = useCase.updateArticle(requestMapper.toDomain(request));
        return responseMapper.toResponse(article);
    }

    @Operation(summary = "Удалить статью")
    @DeleteMapping(value = "/{id}")
    public void deleteArticle(@PathVariable Long id) {
        useCase.deleteArticle(id);
    }
}
