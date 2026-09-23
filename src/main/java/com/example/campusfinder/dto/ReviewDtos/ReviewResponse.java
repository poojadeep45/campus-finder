package com.example.campusfinder.dto.ReviewDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long campusId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
