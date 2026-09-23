package com.example.campusfinder.dto.ProgramDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramResponse {
    private Long id;
    private String programName;
    private String degreeType;
    private String duration;
    private String description;
    private BigDecimal tuitionFee;
    private String eligibilityCriteria;
    private Long campusId;
    private String campusName;
}
