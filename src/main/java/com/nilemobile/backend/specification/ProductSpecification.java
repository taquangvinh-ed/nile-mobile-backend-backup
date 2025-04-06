package com.nilemobile.backend.specification;

import com.nilemobile.backend.model.Categories;
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

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%"
            );
        };
    }


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

    public static Specification<Product> hasSecondLevel(String secondLevel) {
        return (root, query, criteriaBuilder) -> {
            if (secondLevel == null || secondLevel.trim().isEmpty()) {
                return null;
            }

            // Tham gia với bảng Categories (danh mục cấp 3)
            Join<Product, Categories> categoryJoin = root.join("categories");

            // Tham gia với danh mục cha (danh mục cấp 2)
            Join<Categories, Categories> parentCategoryJoin = categoryJoin.join("parentCategory");

            // Kiểm tra danh mục cấp 2 (level = 2) và tên khớp với secondLevel
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(parentCategoryJoin.get("level"), 2));
            predicates.add(criteriaBuilder.equal(parentCategoryJoin.get("name"), secondLevel));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> hasThirdLevel(String thirdLevel) {
        return (root, query, criteriaBuilder) -> {
            if (thirdLevel == null || thirdLevel.trim().isEmpty()) {
                return null;
            }

            // Tham gia với bảng Categories (danh mục cấp 3)
            Join<Product, Categories> categoryJoin = root.join("categories");

            // Kiểm tra danh mục cấp 3 (level = 3) và tên khớp với thirdLevel
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(categoryJoin.get("level"), 3));
            predicates.add(criteriaBuilder.equal(categoryJoin.get("name"), thirdLevel));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    // Kết hợp các điều kiện lọc
    public static Specification<Product> filterByKeywordPriceBatteryAndScreenSize(
            String keyword,
            Integer minBattery, Integer maxBattery,
            Float minScreenSize, Float maxScreenSize,
            Long minPrice, Long maxPrice,
            String secondLevel, String thỉrdLevel) {
        return Specification.where(hasKeyword(keyword))
                .and(hasBatteryCapacity(minBattery, maxBattery))
                .and(hasScreenSize(minScreenSize, maxScreenSize))
                .and(hasPriceRange(minPrice, maxPrice))
                .and(hasSecondLevel(secondLevel))
                .and(hasThirdLevel(thỉrdLevel))
                .and(inStock());
    }
}
