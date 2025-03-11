package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.CartException;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.request.AddCartItemRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImp implements CartService {

    private CartRepository cartRepository;


    private CartItemService cartItemService;

    private ProductService productService;

    private VariationService variationService;

    public CartServiceImp(CartRepository cartRepository, CartItemService cartItemService, ProductService productService, VariationService variationService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.variationService = variationService;
    }

    @Transactional
    public Cart getCartById(Long cartId) {
        Cart cart = cartRepository.findByCartIdWithItems(cartId)
                .orElseThrow(() -> new CartException("Cart not found with ID: " + cartId));
        return cart;
    }

    @Transactional
    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserIdWithItems(userId)
                .orElseThrow(() -> new CartException("Cart not found for user ID: " + userId));
        return cart;
    }

    @Override
    public Cart createCart(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setSubtotal(0L);
        cart.setTotalItems(0);
        cart.setTotalDiscountPrice(0L);
        return cartRepository.save(cart);
    }

//    @Override
//    public String addCartItem(Long userId, AddCartItemRequest addCartItemRequest) throws ProductException {
//        if (userId == null || addCartItemRequest == null) {
//            throw new IllegalArgumentException("UserId or AddCartItemRequest cannot be null");
//        }
//
//        Cart cart = findUserCart(userId);
//        if (cart == null) {
//            throw new ProductException("Cart not found for user with id: " + userId);
//        }
//
//        Variation variation = variationService.findVariationById(addCartItemRequest.getVariationId());
//
//        Product product = productService.findProductById(addCartItemRequest.getProductId());
//        if (!variation.getProduct().getId().equals(product.getId())) {
//            throw new ProductException("Variation does not belong to the specified product");
//        }
//
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(cart);
//        cartItem.setVariation(variation);
//        cartItem.setQuantity(addCartItemRequest.getQuantity());
//        cartItem.setSubtotal(addCartItemRequest.getQuantity() * variation.getPrice());
//
//        CartItem savedCartItem = cartItemService.createCartItem(cartItem, userId);
//
//        cart.getCartItems().add(savedCartItem);
//        cart.calculateSubtotal();
//        cartRepository.save(cart);
//        return ("CartItem was added successfully!");
//    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId);
        return cart;
    }


    @Override
    public void clearCart(Long userId) throws ProductException {

    }
}
