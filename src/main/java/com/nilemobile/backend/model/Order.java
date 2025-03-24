package com.nilemobile.backend.model;

import com.nilemobile.backend.contant.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "TotalPrice", nullable = false)
    private Long totalPrice;

    @Column(name = "TotalDiscountPrice", nullable = false)
    private Long totalDiscountPrice;

    @ManyToOne
    @JoinColumn(name = "U_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToOne
    @JoinColumn(name = "paymentDetail_id", referencedColumnName = "id")
    private PaymentDetails paymentDetails;

    @OneToOne
    @JoinColumn(name = "addressId", referencedColumnName = "address_id", nullable = true)
    private Address shippingAddress;

    private int totalItem;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "Status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
        this.status = OrderStatus.PLACED;
        this.totalPrice = 0L;
        this.totalDiscountPrice = 0L;
        this.totalItem = 0;
    }

    public Order(Long id, LocalDateTime orderDate, Long totalPrice, Long totalDiscountPrice, User user, List<OrderDetail> orderDetails, PaymentDetails paymentDetails, Address shippingAddress, int totalItem, LocalDateTime createAt, OrderStatus status) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.totalDiscountPrice = totalDiscountPrice;
        this.user = user;
        this.orderDetails = orderDetails;
        this.paymentDetails = paymentDetails;
        this.shippingAddress = shippingAddress;
        this.totalItem = totalItem;
        this.createAt = createAt;
        this.status = status;
    }

    public Long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(Long totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
