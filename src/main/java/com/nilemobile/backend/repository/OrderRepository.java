package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Override
    List<Order> findAll();

    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> findByUserId(Long userId);
}
