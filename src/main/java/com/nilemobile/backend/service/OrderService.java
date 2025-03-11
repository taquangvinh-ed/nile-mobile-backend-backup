package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(Long orderId) throws Orderexception;

    public List<Order> orderHistory(Long userId);

    public Order placedOrder(Long orderId) throws Orderexception;

    public Order confirmOrder(Long orderId) throws Orderexception;

    public Order shippedOrder(Long orderId) throws Orderexception;

    public Order deliveredOrder(Long orderId) throws Orderexception;

    public Order canceledOrder(Long orderId) throws Orderexception;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws Orderexception;
}
