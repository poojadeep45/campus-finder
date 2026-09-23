package com.example.campusfinder.mapper;

import com.example.campusfinder.dto.ProgramDtos.ProgramResponse;
import com.example.campusfinder.dto.ReviewDtos.ReviewResponse;
import com.example.campusfinder.dto.campusDtos.CampusDetailsResponse;
import com.example.campusfinder.dto.campusDtos.CampusRequest;
import com.example.campusfinder.dto.campusDtos.CampusResponse;
import com.example.campusfinder.entities.Campus;
import com.example.campusfinder.entities.Facility;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CampusMapper {

    public Campus toEntity(CampusRequest request) {
        return Campus.builder()
                .universityName(request.getUniversityName())
                .campusName(request.getCampusName())
                .description(request.getDescription())
                .address(request.getAddress())
                .city(request.getCity())
                .province(request.getProvince())
                .country(request.getCountry())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .website(request.getWebsite())
                .contactNumber(request.getContactNumber())
                .email(request.getEmail())
                .averageTuitionFee(request.getAverageTuitionFee())
                .admissionRequirements(request.getAdmissionRequirements())
                .campusImage(request.getCampusImage())
                .build();
    }

    public void updateEntity(Campus campus , CampusRequest request) {
        campus.setUniversityName(campus.getUniversityName());
        campus.setCampusName(campus.getCampusName());
        campus.setDescription(campus.getDescription());
        campus.setAddress(request.getAddress());
        campus.setCity(request.getCity());
        campus.setProvince(request.getProvince());
        campus.setCountry(request.getCountry());
        campus.setLatitude(request.getLatitude());
        campus.setLongitude(request.getLongitude());
        campus.setWebsite(request.getWebsite());
        campus.setContactNumber(request.getContactNumber());
        campus.setEmail(request.getEmail());
        campus.setAverageTuitionFee(request.getAverageTuitionFee());
        campus.setAdmissionRequirements(request.getAdmissionRequirements());
        campus.setCampusImage(request.getCampusImage());
    }

    public CampusResponse toResponse(Campus campus, Double avgRating , Long totalReviews){
        return CampusResponse.builder()
                .id(campus.getId())
                .universityName(campus.getUniversityName())
                .campusName(campus.getCampusName())
                .description(campus.getDescription())
                .address(campus.getAddress())
                .city(campus.getCity())
                .province(campus.getProvince())
                .country(campus.getCountry())
                .latitude(campus.getLatitude())
                .longitude(campus.getLongitude())
                .website(campus.getWebsite())
                .contactNumber(campus.getContactNumber())
                .email(campus.getEmail())
                .averageTuitionFee(campus.getAverageTuitionFee())
                .admissionRequirements(campus.getAdmissionRequirements())
                .campusImage(campus.getCampusImage())
                .facilities(facilityNames (campus.getFacilities()))
                .averageRating(avgRating)
                .totalReviews(totalReviews)
                .createdDate(campus.getCreatedDate())
                .updatedDate(campus.getUpdatedDate())
                .build();
    }

    public CampusDetailsResponse toDetailsResponse(Campus campus,
                                                   List<ProgramResponse> programs,
                                                   Double avgRating , Long totalReviews,
                                                   List<ReviewResponse> reviews){
        return CampusDetailsResponse.builder()
                .id(campus.getId())
                .universityName(campus.getUniversityName())
                .campusName(campus.getCampusName())
                .description(campus.getDescription())
                .address(campus.getAddress())
                .city(campus.getCity())
                .province(campus.getProvince())
                .country(campus.getCountry())
                .latitude(campus.getLatitude())
                .longitude(campus.getLongitude())
                .website(campus.getWebsite())
                .contactNumber(campus.getContactNumber())
                .email(campus.getEmail())
                .averageTuitionFee(campus.getAverageTuitionFee())
                .admissionRequirements(campus.getAdmissionRequirements())
                .campusImage(campus.getCampusImage())
                .facilities(facilityNames(campus.getFacilities()))
                .programs(programs)
                .averageRating(avgRating)
                .totalReviews(totalReviews)
                .reviews(reviews)
                .createdDate(campus.getCreatedDate())
                .updatedDate(campus.getUpdatedDate())
                .build();
    }

    private Set<String> facilityNames(Set<Facility> facilities) {
        if (facilities == null) return Set.of();
        return facilities.stream().map(Facility::getName).collect(Collectors.toSet());
    }
}
