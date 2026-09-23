package com.example.campusfinder.service;

import com.example.campusfinder.dto.authDtos.UserResponse;
import com.example.campusfinder.entities.User;
import com.example.campusfinder.exception.DuplicateResourceException;
import com.example.campusfinder.exception.ResourceNotFoundException;
import com.example.campusfinder.mapper.UserMapper;
import com.example.campusfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserResponse getCurrentUserProfile(String username) {
        User user = findByUserName(username);
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateProfile(String username, String fullName, String email) {
        User user = findByUserName(username);
        if (email != null && !email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already registered: " + email);
        }

        if (fullName != null) user.setFullName(fullName);
        if (email != null) user.setEmail(email);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Override
    public void setUserEnabled(Long userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    private User findByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found:" + username));
    }
}
