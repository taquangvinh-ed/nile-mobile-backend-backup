package com.nilemobile.backend.reponse;

public class OrderDetailDTO {
    private Long id;
    private Long variationId;
    private int quantity;
    private long subtotal;

    // Constructor, Getters, Setters
    public OrderDetailDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVariationId() { return variationId; }
    public void setVariationId(Long variationId) { this.variationId = variationId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getSubtotal() { return subtotal; }
    public void setSubtotal(long subtotal) { this.subtotal = subtotal; }
}
