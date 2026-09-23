package com.example.campusfinder.service;

import com.example.campusfinder.dto.authDtos.AuthResponse;
import com.example.campusfinder.dto.authDtos.LoginRequest;
import com.example.campusfinder.dto.authDtos.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
