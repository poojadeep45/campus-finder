package com.example.campusfinder.controller;

import com.example.campusfinder.dto.favoriteDto.FavoriteResponse;
import com.example.campusfinder.service.FavoriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "Manage a user's favorite campuses")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/{campusId}")
    public ResponseEntity<FavoriteResponse> addFavorite(Authentication authentication, @PathVariable Long campusId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.addFavorite(authentication.getName(), campusId));
    }

    @DeleteMapping("/{campusId}")
    public ResponseEntity<Void> removeFavorite(Authentication authentication, @PathVariable Long campusId) {
        favoriteService.removeFavorite(authentication.getName(), campusId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites(Authentication authentication) {
        return ResponseEntity.ok(favoriteService.getFavorites(authentication.getName()));
    }
}
