package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.reponse.VariationDTO;
import com.nilemobile.backend.request.CreateVariationRequest;
import com.nilemobile.backend.service.VariationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class VariationController {
    private VariationService variationService;

    public VariationController(VariationService variationService) {
        this.variationService = variationService;
    }

    @PostMapping("/create-variation")
    public ResponseEntity<VariationDTO> createVariation(@Valid @RequestBody CreateVariationRequest request) throws ProductException {
        Variation variation = variationService.createVariation(request);
        VariationDTO variationDTO = new VariationDTO(variation);
        return ResponseEntity.status(HttpStatus.CREATED).body(variationDTO);
    }
}