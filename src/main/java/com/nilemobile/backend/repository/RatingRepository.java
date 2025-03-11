package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
