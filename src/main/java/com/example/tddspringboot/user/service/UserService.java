package com.example.tddspringboot.user.service;

import com.example.tddspringboot.user.domain.User;
import com.example.tddspringboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    public User add(User user) {
        return userRepository.save(user);
    }

}
