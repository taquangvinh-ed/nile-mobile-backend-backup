package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.request.CreateVariationRequest;

import java.util.List;

public interface VariationService {
    Variation findVariationById(Long variationId) throws ProductException;

    boolean isVariationInStock(Long variationId, int quantity) throws ProductException;

    Variation updateVariationStock(Long variationId, int quantity) throws ProductException;

    List<Variation> findVariationsByProductId(Long productId) throws ProductException;

    Variation updateVariationPrice(Long variationId, Long newPrice) throws ProductException;

    Variation createVariation(CreateVariationRequest request) throws ProductException;
}
