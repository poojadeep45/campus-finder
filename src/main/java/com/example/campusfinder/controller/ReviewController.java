package com.example.campusfinder.controller;

import com.example.campusfinder.dto.ReviewDtos.ReviewRequest;
import com.example.campusfinder.dto.ReviewDtos.ReviewResponse;
import com.example.campusfinder.dto.errorAndPagingDtos.PageResponse;
import com.example.campusfinder.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "Ratings and reviews for campuses")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/campus/{campusId}")
    public ResponseEntity<ReviewResponse> addReview(Authentication authentication, @PathVariable Long campusId,
                                                    @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.addReview(authentication.getName(), campusId, request));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(Authentication authentication, @PathVariable Long reviewId,
                                                       @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(authentication.getName(), reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(Authentication authentication, @PathVariable Long reviewId) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_ADMIN"));
        reviewService.deleteReview(authentication.getName(), reviewId, isAdmin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/campus/{campusId}")
    public ResponseEntity<PageResponse<ReviewResponse>> getReviewsByCampus(
            @PathVariable Long campusId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(PageResponse.from(reviewService.getReviewsByCampus(campusId, pageable)));
    }
}
