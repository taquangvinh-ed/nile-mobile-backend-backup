package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetails, Long> {
}
