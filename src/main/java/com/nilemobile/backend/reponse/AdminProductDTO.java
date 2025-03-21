package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.Product;

public class AdminProductDTO {
    private Long productId;
    private String name;
    private String categoryName;
    private Float screenSize;
    private String displayTech;
    private String refreshRate;
    private String resolution;
    private String frontCamera;
    private String backCamera;
    private String chipset;
    private String cpu;
    private String gpu;
    private Integer batteryCapacity;
    private String chargingPort;
    private String os;
    private String productSize;
    private Float productWeight;
    private String description;

    public AdminProductDTO(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.categoryName = product.getCategories() != null ? product.getCategories().getName() : null;;
        this.screenSize = product.getScreenSize();
        this.displayTech = product.getDisplayTech();
        this.refreshRate = product.getRefreshRate();
        this.resolution = product.getResolution();
        this.frontCamera = product.getFrontCamera();
        this.backCamera = product.getBackCamera();
        this.chipset = product.getChipset();
        this.cpu = product.getCpu();
        this.gpu = product.getGpu();
        this.batteryCapacity = product.getBatteryCapacity();
        this.chargingPort = product.getChargingPort();
        this.os = product.getOs();
        this.productSize = product.getProductSize();
        this.productWeight = product.getProductWeight();
        this.description = product.getDescription();
    }

    public AdminProductDTO(Long productId, String name, String categoryName, Float screenSize, String displayTech, String refreshRate, String resolution, String frontCamera, String backCamera, String chipset, String cpu, String gpu, Integer batteryCapacity, String chargingPort, String os, String productSize, Float productWeight, String description) {
        this.productId = productId;
        this.name = name;
        this.categoryName = categoryName;
        this.screenSize = screenSize;
        this.displayTech = displayTech;
        this.refreshRate = refreshRate;
        this.resolution = resolution;
        this.frontCamera = frontCamera;
        this.backCamera = backCamera;
        this.chipset = chipset;
        this.cpu = cpu;
        this.gpu = gpu;
        this.batteryCapacity = batteryCapacity;
        this.chargingPort = chargingPort;
        this.os = os;
        this.productSize = productSize;
        this.productWeight = productWeight;
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Float screenSize) {
        this.screenSize = screenSize;
    }

    public String getDisplayTech() {
        return displayTech;
    }

    public void setDisplayTech(String displayTech) {
        this.displayTech = displayTech;
    }

    public String getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(String refreshRate) {
        this.refreshRate = refreshRate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(String frontCamera) {
        this.frontCamera = frontCamera;
    }

    public String getBackCamera() {
        return backCamera;
    }

    public void setBackCamera(String backCamera) {
        this.backCamera = backCamera;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getChargingPort() {
        return chargingPort;
    }

    public void setChargingPort(String chargingPort) {
        this.chargingPort = chargingPort;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Float getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Float productWeight) {
        this.productWeight = productWeight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
