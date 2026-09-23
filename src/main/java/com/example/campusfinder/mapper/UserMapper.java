package com.example.campusfinder.mapper;

import com.example.campusfinder.dto.authDtos.UserResponse;
import com.example.campusfinder.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .createdDate(user.getCreatedDate())
                .build();
    }
}
