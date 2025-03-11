package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.CartItemException;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.Variation;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem, Long userId);

    public CartItem updateCartItem(Long userId, Long cartItemId, int quantity) throws CartItemException;

    public Boolean isCartItemExist(Cart cart, Variation variation, Long userId);


    public CartItem removeCartItem(Long userId, Long cartItemId) throws CartItemException;


}
