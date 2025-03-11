package com.nilemobile.backend.controller;

import com.nilemobile.backend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cart/items")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

//    @PutMapping
//    public
}
