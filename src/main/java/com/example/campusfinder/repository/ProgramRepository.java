package com.example.campusfinder.repository;

import com.example.campusfinder.entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByCampusId(Long campusId);
}
