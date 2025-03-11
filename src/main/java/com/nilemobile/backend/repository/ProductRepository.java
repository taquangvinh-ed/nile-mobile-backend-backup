package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.variations v " +
            "JOIN p.categories c " +
            "WHERE (:firstLevel IS NULL OR c.parentCategory.parentCategory.name = :firstLevel) " +
            "AND (:secondLevel IS NULL OR c.parentCategory.name = :secondLevel) " +
            "AND (:thirdLevel IS NULL OR c.name = :thirdLevel) " +
            "AND (:ram IS NULL OR v.ram IN :ram) " +
            "AND (:minPrice IS NULL OR v.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR v.price <= :maxPrice) " +
            "AND (v IS NOT NULL AND v.stockQuantity > 0) " +
            "GROUP BY p.id, p.name, p.screenSize, p.displayTech, p.resolution, p.refreshRate, p.frontCamera, p.backCamera, p.chipset, p.batteryCapacity, p.chargingPort, p.os, p.productSize, p.productWeight, p.description, p.imageURL, p.createAt, c.id " +
            "ORDER BY CASE WHEN :sort = 'price_asc' THEN MIN(v.price) END ASC, " +
            "         CASE WHEN :sort = 'price_desc' THEN MIN(v.price) END DESC, " +
            "         CASE WHEN :sort = 'newest' THEN p.createAt END DESC")
    Page<Product> filterProducts(
            @Param("firstLevel") String firstLevel,
            @Param("secondLevel") String secondLevel,
            @Param("thirdLevel") String thirdLevel,
            @Param("ram") List<String> ram,
            @Param("rom") List<String> rom,
            @Param("os") String os,
            @Param("minBattery") Integer minBattery,
            @Param("maxBattery") Integer maxBattery,
            @Param("minScreenSize") Float minScreenSize,
            @Param("maxScreenSize") Float maxScreenSize,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice,
            @Param("minDiscount") Integer minDiscount,
            @Param("sort") String sort,
            Pageable pageable);

}
