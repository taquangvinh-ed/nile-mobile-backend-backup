package com.nilemobile.backend.service;

import com.nilemobile.backend.model.PaymentMethod;

public interface PaymentDetailService {
    public String updatePaymentMethod(Long orderId, PaymentMethod paymentMethod);
}
