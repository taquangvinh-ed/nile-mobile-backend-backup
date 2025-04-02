package com.nilemobile.backend.specification;

import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> hasBatteryCapacity(Integer minBattery, Integer maxBattery) {
        return (root, query, criteriaBuilder) -> {
            if (minBattery == null && maxBattery == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (minBattery != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("batteryCapacity"), minBattery));
            }
            if (maxBattery != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("batteryCapacity"), maxBattery));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Lọc theo kích thước màn hình
    public static Specification<Product> hasScreenSize(Float minScreenSize, Float maxScreenSize) {
        return (root, query, criteriaBuilder) -> {
            if (minScreenSize == null && maxScreenSize == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (minScreenSize != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("screenSize"), minScreenSize));
            }
            if (maxScreenSize != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("screenSize"), maxScreenSize));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Lọc theo giá (dựa trên discountPrice)
    public static Specification<Product> hasPriceRange(Long minPrice, Long maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Variation> variationRoot = subquery.from(Variation.class);
            subquery.select(criteriaBuilder.min(variationRoot.get("discountPrice")))
                    .where(
                            criteriaBuilder.equal(variationRoot.get("product"), root),
                            criteriaBuilder.greaterThan(variationRoot.get("stockQuantity"), 0)
                    );
            List<Predicate> predicates = new ArrayList<>();
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(subquery, minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(subquery, maxPrice));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Lọc sản phẩm còn hàng (stockQuantity > 0)
    public static Specification<Product> inStock() {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Variation> variationJoin = root.join("variations");
            return criteriaBuilder.greaterThan(variationJoin.get("stockQuantity"), 0);
        };
    }

    // Kết hợp các điều kiện lọc
    public static Specification<Product> filterByPriceBatteryAndScreenSize(
            Integer minBattery, Integer maxBattery,
            Float minScreenSize, Float maxScreenSize,
            Long minPrice, Long maxPrice) {
        return Specification.where(hasBatteryCapacity(minBattery, maxBattery))
                .and(hasScreenSize(minScreenSize, maxScreenSize))
                .and(hasPriceRange(minPrice, maxPrice))
                .and(inStock());
    }
}
