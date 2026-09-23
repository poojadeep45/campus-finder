package com.example.campusfinder.service;

import com.example.campusfinder.dto.favoriteDto.FavoriteResponse;

import java.util.List;

public interface FavoriteService {
    FavoriteResponse addFavorite(String username,  Long campusId);
    void removeFavorite(String username,  Long campusId);
    List<FavoriteResponse> getFavorites(String username);
}
