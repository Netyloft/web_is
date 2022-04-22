package com.example.web_is.usecase;

import com.example.web_is.data.User;
import com.example.web_is.filter.UserFilter;

import java.util.List;


public interface UserUseCase {
    User getUser(Long id);
    List<User> getUsers(UserFilter filter);
    User createUser(User id);
}
