package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.CartItemException;
import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.User;
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
@RequestMapping("/api/cart/items") // Thêm dấu "/" đầu tiên
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CartItemDTO> addCartItemToCart(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddCartItemRequest req) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        CartItem cartItem = new CartItem();
        cartItem.setVariation(req.getVariation());
        cartItem.setCart(req.getCart());

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
        CartItemDTO cartItemDTO = new CartItemDTO(
                variationDTO,
                cartItem.getQuantity(),
                cartItem.getSubtotal(),
                variationDTO.getDiscountPrice()
        );
        cartItemDTO.setDiscountPrice(cartItem.getDiscountPrice());
        return cartItemDTO;
    }
}