package com.example.campusfinder.service;

import com.example.campusfinder.dto.authDtos.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getCurrentUserProfile(String username);
    UserResponse updateProfile(String username, String fullName,  String email);
    List<UserResponse> getAllUsers();
    void setUserEnabled(Long userId ,  boolean enabled);
}
