package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
