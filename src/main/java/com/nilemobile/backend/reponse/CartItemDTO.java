package com.nilemobile.backend.reponse;

import jakarta.persistence.Column;

public class CartItemDTO {
    private Long id;

    private String name;

    VariationDTO variation;

    private Integer quantity;

    private long subtotal = 0L;

    private Long discountPrice;

    public CartItemDTO() {
    }

    public CartItemDTO(String name, Long id, VariationDTO variation, Integer quantity, long subtotal, Long discountPrice) {
        this.name = variation.getName();
        this.id = id;
        this.variation = variation;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.discountPrice= discountPrice;
    }

    public VariationDTO getVariation() {
        return variation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVariation(VariationDTO variation) {
        this.variation = variation;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public long getSubtotal() {
        if (variation != null && quantity != null) {
            return quantity * variation.getPrice();
        }
        return 0L;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }
}
