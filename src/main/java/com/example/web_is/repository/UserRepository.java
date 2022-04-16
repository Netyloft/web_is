package com.example.web_is.repository;

import com.example.jpa.BaseCrudRepository;
import com.example.web_is.data.User;

public interface UserRepository{
    User get(Long id);
    User create(User user);
}
