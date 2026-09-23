package com.example.campusfinder.service;

import com.example.campusfinder.dto.favoriteDto.FavoriteResponse;
import com.example.campusfinder.entities.Campus;
import com.example.campusfinder.entities.Favorite;
import com.example.campusfinder.entities.User;
import com.example.campusfinder.exception.DuplicateResourceException;
import com.example.campusfinder.exception.ResourceNotFoundException;
import com.example.campusfinder.mapper.FavoriteMapper;
import com.example.campusfinder.repository.CampusRepository;
import com.example.campusfinder.repository.FavoriteRepository;
import com.example.campusfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    @Transactional
    public FavoriteResponse addFavorite(String username, Long campusId) {
        User user = findUser(username);
        Campus campus = campusRepository.findById(campusId)
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found with id: " + campusId));

        if (favoriteRepository.existsByUserIdAndCampusId(user.getId(), campusId)) {
            throw new DuplicateResourceException("Campus already in favorites list");
        }
        Favorite favorite = Favorite.builder().user(user).campus(campus).build();
        return favoriteMapper.toResponse(favoriteRepository.save(favorite));
    }

    @Override
    @Transactional
    public void removeFavorite(String username, Long campusId) {
        User user = findUser(username);
        Favorite favorite = favoriteRepository.findByUserIdAndCampusId(user.getId(), campusId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found for campus id: " + campusId));
        favoriteRepository.delete(favorite);
    }

    @Override
    public List<FavoriteResponse> getFavorites(String username) {
        User user = findUser(username);
        return favoriteRepository.findByUserId(user.getId()).stream().map(favoriteMapper::toResponse).toList();
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}
