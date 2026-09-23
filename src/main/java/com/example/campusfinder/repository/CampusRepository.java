package com.example.campusfinder.repository;

import com.example.campusfinder.entities.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CampusRepository extends JpaRepository<Campus, Long>, JpaSpecificationExecutor<Campus> {
}
