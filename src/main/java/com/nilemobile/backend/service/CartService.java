package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.request.AddCartItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddCartItemRequest addCartItemRequest) throws ProductException;

    public Cart findUserCart(Long userId);


    public void clearCart(Long userId) throws ProductException;
}
