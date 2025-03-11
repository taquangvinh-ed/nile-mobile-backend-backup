package com.nilemobile.backend.request;

public class AddCartItemRequest {
    private Long productId;
    private Long variationId;
    private int quantity;
    private Long price;

    public AddCartItemRequest(Long productId, Long variationId, int quantity, Long price) {
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
