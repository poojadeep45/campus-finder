package com.example.campusfinder.service;

import com.example.campusfinder.dto.ReviewDtos.ReviewRequest;
import com.example.campusfinder.dto.ReviewDtos.ReviewResponse;
import com.example.campusfinder.entities.Campus;
import com.example.campusfinder.entities.Review;
import com.example.campusfinder.entities.User;
import com.example.campusfinder.exception.DuplicateResourceException;
import com.example.campusfinder.exception.ResourceNotFoundException;
import com.example.campusfinder.exception.UnauthorizedActionException;
import com.example.campusfinder.mapper.ReviewMapper;
import com.example.campusfinder.repository.CampusRepository;
import com.example.campusfinder.repository.ReviewRepository;
import com.example.campusfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponse addReview(String username, Long campusId, ReviewRequest request) {
        User user = findUser(username);
        Campus campus = campusRepository.findById(campusId)
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found with id: " + campusId));

        boolean alreadyReviewed = campus.getReviews().stream()
                .anyMatch(r -> r.getUser().getId().equals(user.getId()));
        if (alreadyReviewed) {
            throw new DuplicateResourceException("You have already reviewed this campus. Please edit your existing review.");
        }

        Review review = Review.builder()
                .user(user)
                .campus(campus)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(String username, Long reviewId, ReviewRequest request) {
        User user = findUser(username);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException("You can only edit your own reviews");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public void deleteReview(String username, Long reviewId, boolean isAdmin) {
        User user = findUser(username);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        if (!isAdmin && !review.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException("You can only delete your own reviews");
        }
        reviewRepository.delete(review);
    }

    @Override
    public Page<ReviewResponse> getReviewsByCampus(Long campusId, Pageable pageable) {
        if (!campusRepository.existsById(campusId)) {
            throw new ResourceNotFoundException("Campus not found with id: " + campusId);
        }
        return reviewRepository.findByCampusId(campusId, pageable).map(reviewMapper::toResponse);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}
