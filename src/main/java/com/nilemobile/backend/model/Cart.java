package com.nilemobile.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", length = 36)
    private Long cartId;

    @Column(name = "subtotal", nullable = false)
    private long subtotal;

    private Long totalDiscountPrice;

    private int totalDiscountPercent;

    private int totalItems;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();


    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(Long totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public int getTotalDiscountPercent() {
        return totalDiscountPercent;
    }

    public void setTotalDiscountPercent(int totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void calculateSubtotal() {
        this.subtotal = cartItems.stream()
                .mapToLong(CartItem::getSubtotal)
                .sum();
        this.totalItems = cartItems.size();
        this.totalDiscountPrice = cartItems.stream()
                .mapToLong(CartItem::getDiscountPrice)
                .sum();
        this.totalDiscountPercent = cartItems.stream()
                .mapToInt(item -> item.getVariation() != null ? item.getVariation().getDiscountPercent() : 0)
                .sum();
    }
}
