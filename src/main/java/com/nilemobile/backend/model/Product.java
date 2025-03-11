package com.nilemobile.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", length = 36)
    private Long id;

    @Column(name = "name", length = 36, nullable = false)
    private String name;

    @Column(name = "screenSize", length = 20)
    private Float screenSize;

    @Column(name = "displayTech", length = 20)
    private String displayTech;

    @Column(name = "resolution", length = 20)
    private String resolution;

    @Column(name = "refreshRate", length = 20)
    private String refreshRate;

    @Column(name = "frontCamera", length = 50)
    private String frontCamera;

    @Column(name = "backCamera", length = 50)
    private String backCamera;

    @Column(name = "chipset", length = 50)
    private String chipset;

    @Column(name = "BatteryCapacity", length = 20)
    private Integer batteryCapacity;

    @Column(name = "chargingPort", length = 20)
    private String chargingPort;

    @Column(name = "OS", length = 20)
    private String os;

    @Column(name = "productSize", length = 20)
    private Float productSize;

    @Column(name = "productWeight", length = 20)
    private Float productWeight;

    @Column(name = "description", length = 1000)
    private String description;


    @Column(name = "imageURL", length = 1000)
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    private Categories categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Variation> variations = new ArrayList<>();

    private LocalDateTime createAt;

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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(String refreshRate) {
        this.refreshRate = refreshRate;
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

    public Float getProductSize() {
        return productSize;
    }

    public void setProductSize(Float productSize) {
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Categories getCategories() {
        return categories;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
