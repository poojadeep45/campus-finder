package com.example.campusfinder.controller;

import com.example.campusfinder.dto.campusDtos.CampusDetailsResponse;
import com.example.campusfinder.dto.campusDtos.CampusRequest;
import com.example.campusfinder.dto.campusDtos.CampusResponse;
import com.example.campusfinder.dto.errorAndPagingDtos.PageResponse;
import com.example.campusfinder.service.CampusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/campuses")
@Tag(name = "Campuses", description = "Browse, search, and manage campuses")
public class CampusController {

    @Autowired
    private CampusService campusService;

    @GetMapping
    public ResponseEntity<PageResponse<CampusResponse>> getAllCampuses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(PageResponse.from(campusService.getAllCampuses(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampusDetailsResponse> getCampusById(@PathVariable Long id) {
        return ResponseEntity.ok(campusService.getCampusById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<CampusResponse>> searchCampuses(
            @RequestParam(required = false) String universityName,
            @RequestParam(required = false) String campusName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String program,
            @RequestParam(required = false) String degreeType,
            @RequestParam(required = false) BigDecimal minFee,
            @RequestParam(required = false) BigDecimal maxFee,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(PageResponse.from(
                campusService.searchCampuses(universityName, campusName, city, province, program,
                        degreeType, minFee, maxFee, pageable)));
    }

    @PostMapping
    public ResponseEntity<CampusResponse> createCampus(@Valid @RequestBody CampusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campusService.createCampus(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampusResponse> updateCampus(@PathVariable Long id, @Valid @RequestBody CampusRequest request) {
        return ResponseEntity.ok(campusService.updateCampus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id) {
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }
}
