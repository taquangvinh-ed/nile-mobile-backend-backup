package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.Variation;

public class VariationDTO {
    private Long id;
    private String color;
    private String ram;
    private String rom;
    private Long price;
    private Long discountPrice;
    private int discountPercent;
    private Integer stockQuantity;
    private String imageURL;

    public VariationDTO() {
    }

    public VariationDTO(Long id, String color, String ram, String rom, Long price, Long discountPrice, int discountPercent, Integer stockQuantity, String imageURL) {
        this.id = id;
        this.color = color;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.stockQuantity = stockQuantity;
        this.imageURL = imageURL;
    }

    public VariationDTO(Variation variation) {
        this.id = variation.getId();
        this.color = variation.getColor();
        this.ram = variation.getRam();
        this.rom = variation.getRom();
        this.price = variation.getPrice();
        this.discountPrice = variation.getDiscountPrice();
        this.discountPercent = variation.getDiscountPercent();
        this.stockQuantity = variation.getStockQuantity();
        this.imageURL = variation.getImageURL();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
