package com.example.campusfinder.service;

import com.example.campusfinder.dto.campusDtos.CampusDetailsResponse;
import com.example.campusfinder.dto.campusDtos.CampusRequest;
import com.example.campusfinder.dto.campusDtos.CampusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;


public interface CampusService {
    CampusResponse createCampus(CampusRequest request);
    CampusResponse updateCampus(Long id, CampusRequest request);
    void deleteCampus(Long id);
    CampusDetailsResponse  getCampusById(Long id);
    Page<CampusResponse> getAllCampuses(Pageable pageable);
    Page<CampusResponse> searchCampuses(String universityName, String campusName, String city,
                                        String province, String program, String degreeType,
                                        BigDecimal minFee, BigDecimal maxFee, Pageable pageable);
}
