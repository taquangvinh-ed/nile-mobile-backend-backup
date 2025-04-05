package com.nilemobile.backend.specification;

import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    public static Specification<Order> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.conjunction(); // Trả về true nếu userId là null (không lọc)
            }
            Join<Order, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("userId"), userId);
        };
    }

    public static Specification<Order> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty() || status.equalsIgnoreCase("all")) {
                return criteriaBuilder.conjunction(); // Trả về true nếu status là null hoặc "all" (không lọc)
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}
