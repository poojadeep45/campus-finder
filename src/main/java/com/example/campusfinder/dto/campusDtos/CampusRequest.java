package com.example.campusfinder.dto.campusDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CampusRequest {

    @NotBlank
    private String universityName;

    @NotBlank
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
    private Set<String> facilityNames;

}
