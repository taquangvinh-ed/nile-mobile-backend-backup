package com.nilemobile.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String name;

    @Column(name = "screenSize", length = 20)
    @Positive(message = "Kích thước màn hình phải là số dương")
    private Float screenSize;

    @Column(name = "displayTech")
    private String displayTech;

    @Column(name = "resolution", length = 200)
    private String resolution;

    @Column(name = "refreshRate", length = 200)
    private String refreshRate;

    @Column(name = "frontCamera", length = 200)
    private String frontCamera;

    @Column(name = "backCamera", length = 200)
    private String backCamera;

    @Column(name = "chipset", length = 200)
    private String chipset;

    @Column(name = "cpu", length = 200)
    private String cpu;

    @Column(name = "gpu", length = 200)
    private String gpu;

    @Column(name = "BatteryCapacity", length = 20)
    @Positive(message = "Dung lượng pin phải là số dương")
    private Integer batteryCapacity;

    @Column(name = "chargingPort", length = 200)
    private String chargingPort;

    @Column(name = "OS", length = 200)
    private String os;

    @Column(name = "productSize", length = 200)
    private String productSize;

    @Column(name = "productWeight", length = 20)
    private Float productWeight;

    @Column(name = "description", length = 1000)
    private String description;


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

    public void addVariation(Variation variation) {
        variations.add(variation);
        variation.setProduct(this);
    }

    public void removeVariation(Variation variation) {
        variations.remove(variation);
        variation.setProduct(null);
    }
}
