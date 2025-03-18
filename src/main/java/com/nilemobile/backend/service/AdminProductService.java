package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.reponse.AdminProductDTO;
import com.nilemobile.backend.request.AdminCreateProductRequest;

import java.util.List;

public interface AdminProductService {
    public Product createProduct(AdminCreateProductRequest request);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product updatedProduct) throws ProductException;
    public Product findProductById(Long id) throws ProductException;
    public List<AdminProductDTO> getAllProducts();
}
