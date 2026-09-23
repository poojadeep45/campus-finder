package com.example.campusfinder.service;

import com.example.campusfinder.dto.ProgramDtos.ProgramRequest;
import com.example.campusfinder.dto.ProgramDtos.ProgramResponse;
import com.example.campusfinder.entities.Campus;
import com.example.campusfinder.entities.Program;
import com.example.campusfinder.exception.ResourceNotFoundException;
import com.example.campusfinder.mapper.ProgramMapper;
import com.example.campusfinder.repository.CampusRepository;
import com.example.campusfinder.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private ProgramMapper programMapper;

    @Override
    @Transactional
    public ProgramResponse createProgram(ProgramRequest request) {
        Campus campus = campusRepository.findById(request.getCampusId())
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found with id: " + request.getCampusId()));
        Program program = programMapper.toEntity(request , campus);
        return programMapper.toResponse(programRepository.save(program));
    }

    @Override
    @Transactional
    public ProgramResponse updateProgram(Long id , ProgramRequest request) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + id));
        if (!program.getCampus().getId().equals(request.getCampusId())) {
            Campus campus = campusRepository.findById(request.getCampusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Campus not found with id: " + request.getCampusId()));
            program.setCampus(campus);
        }
        programMapper.updateEntity(program , request);
        return programMapper.toResponse(programRepository.save(program));
    }

    @Override
    @Transactional
    public void deleteProgram(Long id) {
        if (!programRepository.existsById(id)) {
            throw new ResourceNotFoundException("Program not found with id: " + id);
        }
        programRepository.deleteById(id);
    }

    @Override
    public ProgramResponse getProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + id));
        return programMapper.toResponse(program);
    }

    @Override
    public List<ProgramResponse> getAllPrograms() {
        return programRepository.findAll().stream().map(programMapper::toResponse).toList();
    }

    @Override
    public List<ProgramResponse> getAllProgramsByCampus(Long campusId) {
        return programRepository.findByCampusId(campusId).stream().map(programMapper::toResponse).toList();
    }
}
