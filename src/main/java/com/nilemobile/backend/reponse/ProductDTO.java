package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.Product;

public class ProductDTO {
    private Long id;
    private String name;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }

    // Getters và setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
