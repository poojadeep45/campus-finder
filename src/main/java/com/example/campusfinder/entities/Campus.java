package com.example.campusfinder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String universityName;

    @NotBlank
    @Column(nullable = false)
    private String campusName;

    @Column(length = 2000)
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

    @Column(length = 2000)
    private String admissionRequirements;

    private String campusImage;

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL,  orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private Set<Program> programs = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "campus_facilities",
            joinColumns = @JoinColumn(name = "campus_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    @Builder.Default
    private Set<Facility> facilities = new HashSet<>();

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL,  orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL,  orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private Set<Favorite> favorites = new HashSet<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}
