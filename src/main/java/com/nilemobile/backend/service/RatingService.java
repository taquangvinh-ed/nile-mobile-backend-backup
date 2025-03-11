package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.VariationException;
import com.nilemobile.backend.model.Rating;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest request, User user) throws VariationException;

    public List<Rating> variationRatings(Long variationId);
}
