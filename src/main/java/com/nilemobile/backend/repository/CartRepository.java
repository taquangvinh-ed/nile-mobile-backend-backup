package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId")
    Cart findCartByUserId(Long userId);
}
