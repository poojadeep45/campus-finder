package com.example.campusfinder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "programs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String programName;

    @NotBlank
    @Column(nullable = false)
    private String degreeType;

    private String duration;

    @Column(length = 2000)
    private String description;

    private BigDecimal tuitionFee;

    @Column(length = 2000)
    private String eligibilityCriteria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id",  nullable = false)
    @JsonIgnore
    private Campus campus;
}
