package com.nilemobile.backend.reponse;

public class OrderDetailDTO {
    private Long id;
    private Long variationId;
    private String variationName;
    private int quantity;
    private long subtotal;
    private Long totalDiscountPrice;
    private String imageURL;

    // Constructor, Getters, Setters
    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Long id, Long variationId, String variationName, String imageURL, Integer quantity, Long subtotal, Long totalDiscountPrice) {
        this.id = id;
        this.variationId = variationId;
        this.variationName = variationName;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.totalDiscountPrice = totalDiscountPrice;
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

    public Long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(Long totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
