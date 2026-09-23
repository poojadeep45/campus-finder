package com.example.campusfinder.controller;

import com.example.campusfinder.dto.authDtos.UserResponse;
import com.example.campusfinder.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Profile", description = "Manage the current user's profile")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile(Authentication authentication,
                                                        @RequestParam(required = false) String fullName,
                                                        @RequestParam(required = false) String email) {
        return ResponseEntity.ok(userService.updateProfile(authentication.getName(), fullName, email));
    }
}
