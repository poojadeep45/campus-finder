package com.example.campusfinder.controller;

import com.example.campusfinder.dto.ProgramDtos.ProgramRequest;
import com.example.campusfinder.dto.ProgramDtos.ProgramResponse;
import com.example.campusfinder.service.ProgramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
@Tag(name = "Programs", description = "Manage academic programs offered by campuses")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @GetMapping
    public ResponseEntity<List<ProgramResponse>> getAllPrograms(
            @RequestParam(required = false) Long campusId) {
        if (campusId != null) {
            return ResponseEntity.ok(programService.getAllProgramsByCampus(campusId));
        }
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponse> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @PostMapping
    public ResponseEntity<ProgramResponse> createProgram(@Valid @RequestBody ProgramRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(programService.createProgram(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramResponse> updateProgram(@PathVariable Long id, @Valid @RequestBody ProgramRequest request) {
        return ResponseEntity.ok(programService.updateProgram(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }
}
