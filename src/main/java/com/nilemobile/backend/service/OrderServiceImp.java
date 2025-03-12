package com.nilemobile.backend.service;

import com.nilemobile.backend.contant.OrderStatus;
import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.mapper.OrderMapper;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.repository.OrderDTO;
import com.nilemobile.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
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
        if(cart == null){
            throw new Orderexception("Không tìm thấy giỏ hàng cho user " + user.getUserId());
        }else{
            Order order = new Order();
            order.setUser(user);
            order.setShippingAddress(shippingAddress);
            order.setOrderDate(LocalDateTime.now());
            order.setCreateAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PLACED.name());

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
        if(optionalOrder.isPresent()){
            Order order= optionalOrder.get();
            if(!order.getStatus().equals(OrderStatus.PLACED.name())){
                throw new Orderexception("Đơn hàng khong thể được xác nhận vì trạng thái của đơn hàng là: "+ order.getStatus());
            }else{
                order.setStatus(OrderStatus.CONFIRMED);
                return orderRepository.save(order);
            }
        }
        throw  new Orderexception("Lỗi");    }

    @Override
    public Order processOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order= optionalOrder.get();
            if(!order.getStatus().equals(OrderStatus.CONFIRMED.name())){
                throw new Orderexception("Đơn hàng khong thể được xử lý vì trạng thái của đơn hàng là: "+ order.getStatus());
            }else{
                order.setStatus(OrderStatus.PROCESSING);
                return orderRepository.save(order);
            }
        }
        throw  new Orderexception("Lỗi");    }

    @Override
    public Order shippedOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order= optionalOrder.get();
            if(!order.getStatus().equals(OrderStatus.PROCESSING.name())){
                throw new Orderexception("Đơn hàng khong thể được xử lý vì trạng thái của đơn hàng là: "+ order.getStatus());
            }else{
                order.setStatus(OrderStatus.SHIPPED);
                return orderRepository.save(order);
            }
        }
        throw  new Orderexception("Lỗi");
    }

    @Override
    public Order deliveredOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order= optionalOrder.get();
            if(!order.getStatus().equals(OrderStatus.SHIPPED.name())){
                throw new Orderexception("Đơn hàng khong thể xác nhận vận chuyển vì trạng thái của đơn hàng là: "+ order.getStatus());
            }else{
                order.setStatus(OrderStatus.DELIVERED);
                return orderRepository.save(order);
            }
        }
        throw  new Orderexception("Lỗi");    }

    @Override
    public Order completeOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order= optionalOrder.get();
            if(!order.getStatus().equals(OrderStatus.DELIVERED.name())){
                throw new Orderexception("Đơn hàng khong thể xác nhận hoàn thành  vì trạng thái của đơn hàng là: "+ order.getStatus());
            }else{
                order.setStatus(OrderStatus.COMPLETED);
                return orderRepository.save(order);
            }
        }
        throw  new Orderexception("Lỗi");    }

    @Override
    public Order canceledOrder(Long orderId) throws Orderexception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            if(!order.getStatus().equals(OrderStatus.PLACED)){
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
        return OrderMapper.toDTOs(orders);    }
}
