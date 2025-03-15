package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.CartItemException;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.request.AddCartItemRequest;
import com.nilemobile.backend.reponse.CartItemDTO;
import com.nilemobile.backend.reponse.VariationDTO;
import com.nilemobile.backend.service.CartItemService;
import com.nilemobile.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping
    public ResponseEntity<CartItemDTO> addCartItemToCart(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddCartItemRequest req) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = new CartItem();
        cartItem.setVariation(req.getVariation());
        cartItem.setCart(cart);
        CartItem createdCartItem = cartItemService.createCartItem(cartItem, userId);

        CartItemDTO cartItemDTO = convertToDTO(createdCartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestParam int quantity) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        CartItem updatedCartItem = cartItemService.updateCartItem(userId, cartItemId, quantity);

        CartItemDTO cartItemDTO = convertToDTO(updatedCartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        cartItemService.removeCartItem(userId, cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        VariationDTO variationDTO = new VariationDTO(cartItem.getVariation());
        long subtotal = cartItem.getVariation().getPrice() * cartItem.getQuantity();
        long discountPrice = (long) (cartItem.getVariation().getPrice() * (cartItem.getVariation().getDiscountPercent() / 100.0)) * cartItem.getQuantity();

        CartItemDTO cartItemDTO = new CartItemDTO(
                cartItem.getId(),
                variationDTO,
                cartItem.getQuantity(),
                subtotal,
                discountPrice
        );
        return cartItemDTO;
    }
}