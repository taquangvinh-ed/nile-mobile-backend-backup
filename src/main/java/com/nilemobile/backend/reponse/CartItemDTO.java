package com.nilemobile.backend.reponse;

import jakarta.persistence.Column;

public class CartItemDTO {

    VariationDTO variationDTO;

    private Integer quantity;

    private long subtotal = 0L;

    private Long discountPrice;

    public CartItemDTO() {
    }

    public CartItemDTO(VariationDTO variationDTO, Integer quantity, long subtotal, Long discountPrice) {
        this.variationDTO = variationDTO;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.discountPrice= discountPrice;
    }

    public VariationDTO getVariationDTO() {
        return variationDTO;
    }

    public void setVariationDTO(VariationDTO variationDTO) {
        this.variationDTO = variationDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public long getSubtotal() {
        if (variationDTO != null && quantity != null) {
            return quantity * variationDTO.getPrice();
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
