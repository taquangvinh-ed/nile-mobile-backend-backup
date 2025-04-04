package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;

public class VariationDTO {
    private Long variationId;
    private String name;
    private String color;
    private String ram;
    private String rom;
    private Long price;
    private Long discountPrice;
    private int discountPercent;
    private Integer stockQuantity;
    private String imageURL;
    private ProductDTO product;

    public VariationDTO() {
        this.variationId = 0L;
        this.price = 0L;
        this.discountPercent = 0;
        this.discountPrice = 0L;
        this.stockQuantity = 0;
    }

    public VariationDTO(Long variationId, String name, String color, String ram, String rom, Long price, Long discountPrice, int discountPercent, Integer stockQuantity, String imageURL) {
        this.name = name;
        this.variationId = variationId;
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
        this.variationId = variation.getId();
        Product product = variation.getProduct();
        this.name = (product != null) ? product.getName() : "Unknown Product";
        //this.name = variation.getProduct().getName();
        this.color = variation.getColor();
        this.ram = variation.getRam();
        this.rom = variation.getRom();
        this.price = variation.getPrice();
        this.discountPrice = variation.getDiscountPrice();
        this.discountPercent = variation.getDiscountPercent();
        this.stockQuantity = variation.getStockQuantity();
        this.imageURL = variation.getImageURL();
        this.product = (product != null) ? new ProductDTO(product) : null;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }


}
