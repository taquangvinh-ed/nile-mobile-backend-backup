package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN p.variations v JOIN p.categories c " +
            "WHERE (:firstLevel IS NULL OR c.name = :firstLevel) " +
            "AND (:secondLevel IS NULL OR EXISTS (SELECT c2 FROM Categories c2 WHERE c2 = c.parentCategory AND c2.name = :secondLevel)) " +
            "AND (:thirdLevel IS NULL OR EXISTS (SELECT c3 FROM Categories c3 WHERE c3 = c.parentCategory.parentCategory AND c3.name = :thirdLevel)) " +
            "AND (:os IS NULL OR p.os = :os) " +
            "AND (:minBattery IS NULL OR p.batteryCapacity >= :minBattery) " +
            "AND (:maxBattery IS NULL OR p.batteryCapacity <= :maxBattery) " +
            "AND (:minScreenSize IS NULL OR p.screenSize >= :minScreenSize) " +
            "AND (:maxScreenSize IS NULL OR p.screenSize <= :maxScreenSize) " +
            "AND (:minPrice IS NULL OR v.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR v.price <= :maxPrice) " +
            "AND (:minDiscount IS NULL OR v.discountPercent >= :minDiscount) " +
            "AND v.stockQuantity > 0")
    List<Product> filterProducts(
            @Param("firstLevel") String firstLevel,
            @Param("secondLevel") String secondLevel,
            @Param("thirdLevel") String thirdLevel,
            @Param("os") String os,
            @Param("minBattery") Integer minBattery,
            @Param("maxBattery") Integer maxBattery,
            @Param("minScreenSize") Float minScreenSize,
            @Param("maxScreenSize") Float maxScreenSize,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice,
            @Param("minDiscount") Integer minDiscount);

}
