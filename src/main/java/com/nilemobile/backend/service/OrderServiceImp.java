package com.nilemobile.backend.service;

import com.nilemobile.backend.contant.OrderStatus;
import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.mapper.OrderMapper;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.reponse.OrderDTO;
import com.nilemobile.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {


    private CartRepository cartRepository;


    private CartService cartService;


    private ProductService productService;


    private OrderRepository orderRepository;

    public OrderServiceImp(CartRepository cartRepository, CartService cartService, ProductService productService, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        Cart cart = cartRepository.findCartByUserId(user.getUserId());
        if (cart == null) {
            throw new Orderexception("Không tìm thấy giỏ hàng cho user " + user.getUserId());
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress); // Có thể null
        order.setOrderDate(LocalDateTime.now());
        order.setCreateAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);


        long totalPrice = cartService.calculateTotalPrice(cart.getCartId());
        int totalItem = cartService.getTotalItems(cart.getCartId());
        long totalDiscount = cartService.getTotalDiscount(cart.getCartId());
        order.setTotalPrice(totalPrice);
        order.setTotalItem(totalItem);
        order.setTotalDiscountPrice(totalDiscount);

        order.setOrderDetails(cartService.convertCartToOrderDetails(cart, order));

        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws Orderexception {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Orderexception("Không tìm thấy đơn hàng với ID: " + orderId));
    }

    @Override
    public List<Order> orderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }


    @Override
    public Order confirmOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.PLACED)) {
                throw new Orderexception("Đơn hàng khong thể được xác nhận vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.CONFIRMED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order processOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.CONFIRMED)) {
                throw new Orderexception("Đơn hàng khong thể được xử lý vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.PROCESSING);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order shippedOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.PROCESSING)) {
                throw new Orderexception("Đơn hàng khong thể được xử lý vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.SHIPPED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order deliveredOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.SHIPPED)) {
                throw new Orderexception("Đơn hàng khong thể xác nhận vận chuyển vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.DELIVERED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order completeOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.DELIVERED)) {
                throw new Orderexception("Đơn hàng khong thể xác nhận hoàn thành  vì trạng thái của đơn hàng là: " + order.getStatus());
            } else {
                order.setStatus(OrderStatus.COMPLETED);
                return orderRepository.save(order);
            }
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public Order canceledOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.getStatus().equals(OrderStatus.PLACED)) {
                throw new Orderexception("Đơn hàng không thể hủy được");
            }
            order.setStatus(OrderStatus.CANCELED);
            return orderRepository.save(order);
        }
        throw new Orderexception("Lỗi");
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void deleteOrder(Long orderId) throws Orderexception {

    }

    @Override
    public List<OrderDTO> filterOrderByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new Orderexception("Trạng thái không được để trống");
        }

        List<Order> orders = orderRepository.findByStatus(status);
        return OrderMapper.toDTOs(orders);
    }

    @Override
    public Order updateShippingAddress(Long orderId, Address shippingAddress) throws Orderexception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Orderexception("Không tìm thấy đơn hàng với ID: " + orderId));

        if (order.getStatus().equals(OrderStatus.SHIPPED) || order.getStatus().equals(OrderStatus.DELIVERED)) {
            throw new Orderexception("Không thể cập nhật địa chỉ cho đơn hàng đã giao.");
        }

        if (order.getShippingAddress() != null && order.getShippingAddress().equals(shippingAddress)) {
            return order; // Giữ nguyên đơn hàng hiện tại, không cần lưu lại
        }

        order.setShippingAddress(shippingAddress);
        return orderRepository.save(order);
    }
}
