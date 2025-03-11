package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.request.CreateVariationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariationServiceImp implements VariationService {
    private VariationRepository variationRepository;
    private ProductService productService;
    private ProductRepository productRepository;


    public VariationServiceImp(VariationRepository variationRepository, ProductService productService, ProductRepository productRepository) {
        this.variationRepository = variationRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Variation findVariationById(Long variationId) throws ProductException {
        if (variationId == null) {
            throw new IllegalArgumentException("Variation ID cannot be null");
        }
        return variationRepository.findById(variationId)
                .orElseThrow(() -> new ProductException("Variation not found with id: " + variationId));
    }

    @Override
    public boolean isVariationInStock(Long variationId, int quantity) throws ProductException {
        if (variationId == null || quantity < 0) {
            throw new IllegalArgumentException("Variation ID or quantity is invalid");
        }
        Variation variation = findVariationById(variationId);
        return variation.getStockQuantity() != null && variation.getStockQuantity() >= quantity;
    }

    @Override
    public Variation updateVariationStock(Long variationId, int quantity) throws ProductException {
        if (variationId == null || quantity < 0) {
            throw new IllegalArgumentException("Variation ID or quantity is invalid");
        }
        Variation variation = findVariationById(variationId);
        Integer currentStock = variation.getStockQuantity();
        if (currentStock == null || currentStock < quantity) {
            throw new ProductException("Insufficient stock for variation with id: " + variationId);
        }
        variation.setStockQuantity(currentStock - quantity);
        return variationRepository.save(variation);
    }

    @Override
    public List<Variation> findVariationsByProductId(Long productId) throws ProductException {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Product product = productService.findProductById(productId);
        return variationRepository.findByProduct(product);
    }

    @Override
    public Variation updateVariationPrice(Long variationId, Long newPrice) throws ProductException {
        if (variationId == null || newPrice == null || newPrice < 0) {
            throw new IllegalArgumentException("Variation ID or new price is invalid");
        }
        Variation variation = findVariationById(variationId);
        variation.setPrice(newPrice);
        return variationRepository.save(variation);
    }

    @Override
    public Variation createVariation(CreateVariationRequest request) throws ProductException {
        if (request == null) {
            throw new IllegalArgumentException("CreateVariationRequest cannot be null");
        }

        Product product = productService.findProductById(request.getProductId());

        Variation variation = new Variation();
        variation.setProduct(product);
        variation.setColor(request.getColor());
        variation.setRam(request.getRam());
        variation.setRom(request.getRom());
        variation.setPrice(request.getPrice());
        variation.setDiscountPrice(request.getDiscountPrice());
        variation.setDiscountPercent(request.getDiscountPercent());
        variation.setStockQuantity(request.getStockQuantity());
        variation.setImageURL(request.getImageURL());

        Variation savedVariation = variationRepository.save(variation);

        product.getVariations().add(savedVariation);
        productRepository.save(product);

        return savedVariation;
    }
}
