package com.example.campusfinder.service;

import com.example.campusfinder.dto.authDtos.AuthResponse;
import com.example.campusfinder.dto.authDtos.LoginRequest;
import com.example.campusfinder.dto.authDtos.RegisterRequest;
import com.example.campusfinder.entities.Role;
import com.example.campusfinder.entities.User;
import com.example.campusfinder.exception.DuplicateResourceException;
import com.example.campusfinder.repository.UserRepository;
import com.example.campusfinder.security.JwtUtil;
import com.example.campusfinder.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(Role.USER)
                .enabled(true)
                .build();

        User saved  = userRepository.save(user);
        UserPrincipal principal = new UserPrincipal(saved);
        String token = jwtUtil.generateToken(principal);

        return AuthResponse.builder()
                .token(token)
                .userId(saved.getId())
                .username(saved.getUsername())
                .role(saved.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));

        UserPrincipal principal = new UserPrincipal(user);
        String token = jwtUtil.generateToken(principal);

        return AuthResponse.builder()
                .token(token)
                .userId(principal.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
