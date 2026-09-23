package com.example.campusfinder.repository;

import com.example.campusfinder.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByCampusId(Long campusId, Pageable pageable);
    Optional<Review> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.campus.id = :campusId")
    Double findAverageRatingByCampusId(@Param("campusId") Long campusId);

    long countByCampusId(Long campusId);
}
