package com.nilemobile.backend.reponse;

public class OrderDetailDTO {
    private Long id;
    private Long variationId;
    private String variationName;
    private int quantity;
    private long subtotal;
    private String imageURL;

    // Constructor, Getters, Setters
    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Long id, Long variationId, String variationName, String imageURL, Integer quantity, Long subtotal) {
        this.id = id;
        this.variationId = variationId;
        this.variationName = variationName;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
