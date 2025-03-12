package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Override
    List<Order> findAll();

    List<Order> findByStatus(String status);

    List<Order> findByUserId(Long userId);
}
