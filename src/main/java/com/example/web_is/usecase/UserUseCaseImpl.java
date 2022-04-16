package com.example.web_is.usecase;

import com.example.web_is.data.User;
import com.example.web_is.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class UserUseCaseImpl implements UserUseCase{

    private final UserRepository repository;

    @Override
    public User getUser(Long id) {
        return repository.get(id);
    }

    @Override
    public User createUser(User user) {
        return repository.create(user);
    }
}
