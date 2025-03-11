package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public OrderServiceImp(CartRepository cartRepository, CartService cartService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws Orderexception {
        return null;
    }

    @Override
    public List<Order> orderHistory(Long userId) {
        return List.of();
    }

    @Override
    public Order placedOrder(Long orderId) throws Orderexception {
        return null;
    }

    @Override
    public Order confirmOrder(Long orderId) throws Orderexception {
        return null;
    }

    @Override
    public Order shippedOrder(Long orderId) throws Orderexception {
        return null;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws Orderexception {
        return null;
    }

    @Override
    public Order canceledOrder(Long orderId) throws Orderexception {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public void deleteOrder(Long orderId) throws Orderexception {

    }
}
