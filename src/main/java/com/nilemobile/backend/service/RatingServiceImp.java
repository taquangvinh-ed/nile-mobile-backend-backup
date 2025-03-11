package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.VariationException;
import com.nilemobile.backend.model.Rating;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.RatingRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImp implements RatingService {

    private RatingService ratingService;
    private VariationRepository variationRepository;
    private RatingRepository ratingRepository;

    public RatingServiceImp(RatingService ratingService, VariationRepository variationRepository, RatingRepository ratingRepository) {
        this.ratingService = ratingService;
        this.variationRepository = variationRepository;
        this.ratingRepository = ratingRepository;

    }

    @Override
    public Rating createRating(RatingRequest request, User user) throws VariationException {
        Optional<Variation> variation = variationRepository.findById(request.getVariationId());

        if (variation.isPresent()) {
            Rating rating = new Rating();
            rating.setVariation(variation.get());
            rating.setRating(request.getRating());
            rating.setUser(user);
            rating.setCreatedAt(LocalDateTime.now());
            return ratingRepository.save(rating);
        }
        return null;
    }

    @Override
    public List<Rating> variationRatings(Long variationId) {
        return List.of();
    }
}
