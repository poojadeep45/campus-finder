package com.example.campusfinder.service;

import com.example.campusfinder.dto.ProgramDtos.ProgramRequest;
import com.example.campusfinder.dto.ProgramDtos.ProgramResponse;
import com.example.campusfinder.entities.Program;

import java.util.List;

public interface ProgramService {
    ProgramResponse createProgram(ProgramRequest request);
    ProgramResponse updateProgram(Long id , ProgramRequest request);
    void deleteProgram(Long id);
    ProgramResponse getProgramById(Long id);
    List<ProgramResponse> getAllPrograms();
    List<ProgramResponse> getAllProgramsByCampus(Long campusId);
}

