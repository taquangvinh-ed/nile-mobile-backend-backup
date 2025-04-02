package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN p.variations v " +
            "JOIN p.categories c " +
            "LEFT JOIN c.parentCategory pc2 " +
            "LEFT JOIN pc2.parentCategory pc1 " +
            "WHERE (:firstLevel IS NULL OR pc1.name = :firstLevel) " +
            "AND (:secondLevel IS NULL OR pc2.name = :secondLevel) " +
            "AND (:thirdLevel IS NULL OR c.name = :thirdLevel) " +
            "AND (:ram IS NULL OR v.ram IN :ram) " +
            "AND (:rom IS NULL OR v.rom IN :rom) " +
            "AND (:os IS NULL OR p.os = :os) " +
            "AND (:minBattery IS NULL OR :maxBattery IS NULL OR p.batteryCapacity IS NULL OR (p.batteryCapacity >= :minBattery AND p.batteryCapacity <= :maxBattery)) " +
            "AND (:minScreenSize IS NULL OR :maxScreenSize IS NULL OR p.screenSize IS NULL OR (p.screenSize >= :minScreenSize AND p.screenSize <= :maxScreenSize)) " +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR " +
            "  (SELECT MIN(v2.discountPrice) FROM Variation v2 WHERE v2.product = p AND v2.stockQuantity > 0) >= :minPrice AND " +
            "  (SELECT MIN(v2.discountPrice) FROM Variation v2 WHERE v2.product = p AND v2.stockQuantity > 0) <= :maxPrice) " +
            "AND (:minDiscount IS NULL OR v.discountPercent >= :minDiscount) " +
            "AND (v IS NOT NULL AND v.stockQuantity > 0) " +
            "ORDER BY CASE WHEN :sort = 'price_asc' THEN " +
            "  (SELECT MIN(v2.discountPrice) FROM Variation v2 WHERE v2.product = p) END ASC, " +
            "CASE WHEN :sort = 'price_desc' THEN " +
            "  (SELECT MIN(v2.discountPrice) FROM Variation v2 WHERE v2.product = p) END DESC, " +
            "CASE WHEN :sort = 'newest' THEN p.createAt END DESC")
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

    @Query("SELECT DISTINCT c.name " +
            "FROM Product p " +
            "JOIN p.categories c " +
            "WHERE c.level = 3")
    List<String> findDistinctThirdLevels();

    @Query("SELECT DISTINCT c2.name " +
            "FROM Product p " +
            "JOIN p.categories c " +
            "JOIN c.parentCategory c2 " +
            "WHERE c2.level = 2")
    List<String> findDistinctSecondLevels();

    @Query("SELECT DISTINCT c.name " +
            "FROM Product p " +
            "JOIN p.categories c " +
            "WHERE c.level = 3 " +
            "AND c.parentCategory.name = :secondLevelName")
    List<String> findThirdLevelsBySecondLevel(@Param("secondLevelName") String secondLevelName);

}
