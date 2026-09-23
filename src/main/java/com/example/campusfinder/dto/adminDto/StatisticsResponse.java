package com.example.campusfinder.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsResponse {
    private long totalUsers;
    private long totalCampuses;
    private long totalPrograms;
    private long totalReviews;
}
