package com.nilemobile.backend.reponse;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private long subtotal;

    private Long totalDiscountPrice;

    private int totalDiscountPercent;

    private int totalItems;

    private List<CartItemDTO> cartItems;

    public CartDTO(long subtotal, Long totalDiscountPrice, int totalDiscountPercent, int totalItems, List<CartItemDTO> cartItem) {
        this.subtotal = subtotal;
        this.totalDiscountPrice = totalDiscountPrice;
        this.totalDiscountPercent = totalDiscountPercent;
        this.totalItems = totalItems;
        this.cartItems = (cartItem != null) ? new ArrayList<>(cartItem) : new ArrayList<>();    }

    public CartDTO() {
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
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

    public List<CartItemDTO> getCartItem() {
        return cartItems;
    }

    public void setCartItem(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
