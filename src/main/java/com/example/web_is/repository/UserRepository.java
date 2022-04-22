package com.example.web_is.repository;

import com.example.web_is.data.User;
import com.example.web_is.filter.UserFilter;

import java.util.List;

public interface UserRepository{
    List<User> getAll();
    List<User> getAll(UserFilter filter);
    User get(Long id);
    User create(User user);
}
