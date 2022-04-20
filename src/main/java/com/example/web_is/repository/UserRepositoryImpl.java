package com.example.web_is.repository;

import com.example.web_is.dao.UserDao;
import com.example.web_is.data.User;
import com.example.web_is.entity.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final UserDao dao;
    private final UserEntityMapper mapper;

    @Override
    public List<User> getAll() {
        return mapper.toDto(dao.findAll());
    }

    @Override
    public User get(Long id) {
        return mapper.toDto(dao.getById(id));
    }

    @Override
    public User create(User user) {
        return mapper.toDto(dao.save(mapper.toEntity(user)));
    }
}
