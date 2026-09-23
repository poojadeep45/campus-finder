package com.example.campusfinder.mapper;

import com.example.campusfinder.dto.ReviewDtos.ReviewResponse;
import com.example.campusfinder.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .username(review.getUser().getUsername())
                .campusId(review.getCampus().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdDate(review.getCreatedDate())
                .updatedDate(review.getUpdatedDate())
                .build();
    }
}
