package com.example.campusfinder.dto.campusDtos;

import com.example.campusfinder.dto.ProgramDtos.ProgramResponse;
import com.example.campusfinder.dto.ReviewDtos.ReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//used for the single-campus "full details" endpoint — includes programs + reviews
public class CampusDetailsResponse {

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
    private List<ProgramResponse>  programs;
    private Double averageRating;
    private Long totalReviews;
    private List<ReviewResponse> reviews;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
