package com.example.campusfinder.dto.ProgramDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProgramRequest {

    @NotBlank
    private String programName;

    @NotBlank
    private String degreeType;

    private String duration;
    private String description;
    private BigDecimal tuitionFee;
    private String eligibilityCriteria;

    @NotNull
    private Long campusId;
}
