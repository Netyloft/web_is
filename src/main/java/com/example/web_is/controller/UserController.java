package com.example.web_is.controller;

import com.example.api.response.ApiResponse;
import com.example.api.response.ApiResponseListData;
import com.example.web_is.data.User;
import com.example.web_is.filter.UserFilter;
import com.example.web_is.request.UserRequest;
import com.example.web_is.request.mapper.UserRequestMapper;
import com.example.web_is.response.ArticleResponse;
import com.example.web_is.response.UserResponse;
import com.example.web_is.response.mapper.UserResponseMapper;
import com.example.web_is.usecase.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.web_is.controller.Constants.USER_API_BASE_PATH;

@Tag(name = "Пользователь")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = USER_API_BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserResponseMapper responseMapper;
    private final UserRequestMapper requestMapper;
    private final UserUseCase useCase;

    @Operation(summary = "Получить пользователя")
    @GetMapping(value = "/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id){
        User user = useCase.getUser(id);
        return responseMapper.toResponse(user);
    }

    @Operation(summary = "Получить пользователей")
    @GetMapping(value = "/")
    public ApiResponse<ApiResponseListData<UserResponse>> getUsers(@RequestParam(required = false) String nickName){
        UserFilter userFilter = new UserFilter();
        userFilter.setNickName(nickName);
        List<User> users = useCase.getUsers(userFilter);
        return responseMapper.toResponse(users);
    }

    @Operation(summary = "Создать пользователя")
    @PostMapping(value = "/create")
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest request){
        User user = useCase.createUser(requestMapper.toDomain(request));
        return responseMapper.toResponse(user);
    }
}
