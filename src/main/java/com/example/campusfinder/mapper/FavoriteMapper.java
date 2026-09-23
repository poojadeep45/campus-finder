package com.example.campusfinder.mapper;

import com.example.campusfinder.dto.favoriteDto.FavoriteResponse;
import com.example.campusfinder.entities.Favorite;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper {
    public FavoriteResponse toResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .campusId(favorite.getCampus().getId())
                .campusName(favorite.getCampus().getCampusName())
                .universityName(favorite.getCampus().getUniversityName())
                .addedDate(favorite.getAddedDate())
                .build();
    }
}
