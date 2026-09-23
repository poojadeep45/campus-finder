package com.example.campusfinder.controller;

import com.example.campusfinder.dto.adminDto.StatisticsResponse;
import com.example.campusfinder.dto.authDtos.UserResponse;
import com.example.campusfinder.service.AdminService;
import com.example.campusfinder.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin-only management and statistics endpoints")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getStatistics() {
        return ResponseEntity.ok(adminService.getStatistics());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<Void> setUserEnabled(@PathVariable Long id, @RequestParam boolean enabled) {
        userService.setUserEnabled(id, enabled);
        return ResponseEntity.noContent().build();
    }
}
