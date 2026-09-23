package com.example.campusfinder.repository;

import com.example.campusfinder.entities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findByNameIgnoreCase(String name);
}
