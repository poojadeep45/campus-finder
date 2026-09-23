package com.example.campusfinder.service;

import com.example.campusfinder.dto.ReviewDtos.ReviewRequest;
import com.example.campusfinder.dto.ReviewDtos.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponse addReview(String username, Long campusId, ReviewRequest request);
    ReviewResponse updateReview(String username, Long reviewId, ReviewRequest request);
    void deleteReview(String username, Long reviewId, boolean isAdmin);
    Page<ReviewResponse> getReviewsByCampus(Long campusId, Pageable pageable);
}
