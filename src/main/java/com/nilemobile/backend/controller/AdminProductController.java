package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.reponse.AdminProductDTO;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.request.AdminCreateProductRequest;
import com.nilemobile.backend.service.AdminProductService;
import com.nilemobile.backend.service.AdminProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")

public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @GetMapping("product/id/{productId}")
    public ResponseEntity<AdminProductDTO> getProductById(@PathVariable Long productId) throws ProductException{
        Product product = adminProductService.findProductById(productId);
        AdminProductDTO adminProductDTO = new AdminProductDTO(product);
        return ResponseEntity.ok(adminProductDTO);
    }

    @PostMapping("/create-product")
    public ResponseEntity<AdminProductDTO> createProductHandler(@RequestBody AdminCreateProductRequest request) throws ProductException {
        Product product = adminProductService.createProduct(request);
        AdminProductDTO adminProductDTO = new AdminProductDTO(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminProductDTO);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<AdminProductDTO>> getAllProducts() {
        List<AdminProductDTO> products = adminProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
