package com.example.springtokendemo.service;

import com.example.springtokendemo.model.User;
import com.example.springtokendemo.model.dto.UserDto;
import com.example.springtokendemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public List<UserDto> getAllUsers() {
        return userMapper.toDto(userRepo.findAll());
    }
}
