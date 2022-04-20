package com.example.web_is.repository;

import com.example.jpa.BaseCrudRepository;
import com.example.web_is.data.User;

import java.util.List;

public interface UserRepository{
    List<User> getAll();
    User get(Long id);
    User create(User user);
}
