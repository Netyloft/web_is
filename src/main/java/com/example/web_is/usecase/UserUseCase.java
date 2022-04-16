package com.example.web_is.usecase;

import com.example.web_is.data.User;


public interface UserUseCase {
    User getUser(Long id);
    User createUser(User id);
}
