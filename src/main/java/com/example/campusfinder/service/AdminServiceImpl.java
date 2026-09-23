package com.example.campusfinder.service;

import com.example.campusfinder.dto.adminDto.StatisticsResponse;
import com.example.campusfinder.repository.CampusRepository;
import com.example.campusfinder.repository.ProgramRepository;
import com.example.campusfinder.repository.ReviewRepository;
import com.example.campusfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public StatisticsResponse getStatistics() {
        return StatisticsResponse.builder()
                .totalUsers(userRepository.count())
                .totalCampuses(campusRepository.count())
                .totalPrograms(programRepository.count())
                .totalReviews(reviewRepository.count())
                .build();
    }
}
