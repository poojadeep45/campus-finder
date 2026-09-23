package com.example.campusfinder.service;

import com.example.campusfinder.dto.ProgramDtos.ProgramResponse;
import com.example.campusfinder.dto.ReviewDtos.ReviewResponse;
import com.example.campusfinder.dto.campusDtos.CampusDetailsResponse;
import com.example.campusfinder.dto.campusDtos.CampusRequest;
import com.example.campusfinder.dto.campusDtos.CampusResponse;
import com.example.campusfinder.entities.Campus;
import com.example.campusfinder.entities.Facility;
import com.example.campusfinder.entities.Program;
import com.example.campusfinder.exception.ResourceNotFoundException;
import com.example.campusfinder.mapper.CampusMapper;
import com.example.campusfinder.mapper.ProgramMapper;
import com.example.campusfinder.mapper.ReviewMapper;
import com.example.campusfinder.repository.CampusRepository;
import com.example.campusfinder.repository.FacilityRepository;
import com.example.campusfinder.repository.ReviewRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CampusServiceImpl implements  CampusService {

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CampusMapper campusMapper;

    @Autowired
    private ProgramMapper programMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    @Transactional
    public CampusResponse createCampus(CampusRequest request) {
        Campus campus = campusMapper.toEntity(request);
        campus.setFacilities(resolveFacilities(request.getFacilityNames()));
        Campus saved =  campusRepository.save(campus);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public CampusResponse updateCampus(Long id ,CampusRequest request) {
        Campus campus = campusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found with id " + id));
        campusMapper.updateEntity(campus, request);
        campus.setFacilities(resolveFacilities(request.getFacilityNames()));
        return toResponse(campusRepository.save(campus));
    }

    @Override
    @Transactional
    public void deleteCampus(Long id) {
        if (!campusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Campus not found with id " + id);
        }
        campusRepository.deleteById(id);
    }

    @Override
    public CampusDetailsResponse getCampusById(Long id) {
        Campus campus = campusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found with id " + id));

        List<ProgramResponse> programs = campus.getPrograms().stream()
                .map(programMapper::toResponse).toList();

        List<ReviewResponse> reviews = reviewRepository.findByCampusId(id, Pageable.unpaged())
                .stream().map(reviewMapper::toResponse).toList();

        Double avgRating = reviewRepository.findAverageRatingByCampusId(id);
        Long totalReviews = reviewRepository.countByCampusId(id);

        return campusMapper.toDetailsResponse(campus, programs , avgRating , totalReviews, reviews);
    }


    @Override
    public Page<CampusResponse> getAllCampuses(Pageable pageable) {
        return campusRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public Page<CampusResponse> searchCampuses(String universityName, String campusName, String city,
                                               String province, String program, String degreeType,
                                               BigDecimal minFee, BigDecimal maxFee, Pageable pageable) {
        Specification<Campus> spec = buildSpecification(universityName, campusName, city, province,
                program, degreeType, minFee, maxFee);
        return campusRepository.findAll(spec, pageable).map(this::toResponse);
    }

    private Specification<Campus> buildSpecification(String universityName, String campusName, String city,
                                                     String province, String program, String degreeType,
                                                     BigDecimal minFee, BigDecimal maxFee) {
        return (root , query , cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);

            if (universityName != null &&  !universityName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("universityName")), "%" + universityName.toLowerCase() + "%"));
            }
            if (campusName != null &&  !campusName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("campusName")), "%" + campusName.toLowerCase() + "%"));
            }
            if (city != null &&  !city.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
            }
            if (province != null && !province.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("province")), "%" + province.toLowerCase() + "%"));
            }
            if(minFee != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("averageTuitionFee"), minFee));
            }
            if (maxFee != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("averageTuitionFee"), maxFee));
            }
            if (program != null && !program.isBlank() || degreeType != null && !degreeType.isBlank()) {
                Join<Campus, Program> programJoin = root.join("programs");
                if (program != null && !program.isBlank()) {
                    predicates.add(cb.like(cb.lower(programJoin.get("programName")), "%" + program.toLowerCase() + "%"));
                }
                if (degreeType != null && !degreeType.isBlank()) {
                    predicates.add(cb.like(cb.lower(programJoin.get("degreeType")), "%" + degreeType.toLowerCase() + "%"));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Set<Facility> resolveFacilities(Set<String> names) {
        Set<Facility>  facilities = new HashSet<>();
        if (names == null) return facilities;
        for (String facilityName : names) {
            Facility facility = facilityRepository.findByNameIgnoreCase(facilityName)
                    .orElseGet(() -> facilityRepository.save(Facility.builder().name(facilityName).build()));
            facilities.add(facility);
        }
        return facilities;
    }

    private CampusResponse toResponse(Campus campus) {
        Double avgRating = reviewRepository.findAverageRatingByCampusId(campus.getId());
        Long totalReviews = reviewRepository.countByCampusId(campus.getId());
        return campusMapper.toResponse(campus, avgRating, totalReviews);
    }

}
