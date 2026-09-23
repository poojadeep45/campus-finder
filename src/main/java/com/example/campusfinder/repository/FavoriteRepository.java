package com.example.campusfinder.repository;

import com.example.campusfinder.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Optional<Favorite> findByUserIdAndCampusId(Long  userId, Long campusId);
    boolean existsByUserIdAndCampusId(Long userId, Long campusId);
}
