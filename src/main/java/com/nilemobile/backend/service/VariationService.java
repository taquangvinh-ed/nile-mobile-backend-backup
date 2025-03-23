package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.exception.VariationException;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.reponse.VariationDTO;
import com.nilemobile.backend.reponse.VariationDTO2;
import com.nilemobile.backend.request.CreateVariationRequest;

import java.util.List;

public interface VariationService {
    Variation findVariationById(Long variationId) throws ProductException;

    public List<VariationDTO2> getAllVariations();

    public List<VariationDTO> getVariationsByProductId(Long productId) throws VariationException;

    public void deleteVariationById(Long variationId);

    public Variation createVariation(CreateVariationRequest request) throws ProductException;

    public Variation updateVariation(Long variationId, VariationDTO variationDTO) throws VariationException;

    boolean isVariationInStock(Long variationId, int quantity) throws ProductException;

    Variation updateVariationStock(Long variationId, int quantity) throws ProductException;

    List<Variation> findVariationsByProductId(Long productId) throws ProductException;

    Variation updateVariationPrice(Long variationId, Long newPrice) throws ProductException;

}
