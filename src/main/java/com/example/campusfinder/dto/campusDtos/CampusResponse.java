package com.example.campusfinder.dto.campusDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampusResponse {  //used in lists/search

    private Long id;
    private String universityName;
    private String campusName;
    private String description;
    private String address;
    private String city;
    private String province;
    private String country;
    private String longitude;
    private String latitude;
    private String website;
    private String contactNumber;
    private String email;
    private BigDecimal averageTuitionFee;
    private String admissionRequirements;
    private String campusImage;
    private Set<String> facilities;
    private Double averageRating;
    private Long totalReviews;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
