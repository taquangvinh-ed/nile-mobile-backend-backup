package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Review;
import com.nilemobile.backend.model.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVariation(Variation variation);
}
