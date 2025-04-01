package com.nilemobile.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String role="USER";

    private String phoneNumber;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, targetEntity = Address.class, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

//    @Embedded
//    @ElementCollection
//    @CollectionTable(name = "payment_information", joinColumns = @JoinColumn(name = "user_id"))
//    private List<PaymentInformation> paymentInformations = new ArrayList<>();



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    private LocalDateTime createdDateAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }




    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public LocalDateTime getCreatedDateAt() {
        return createdDateAt;
    }

    public void setCreatedDateAt(LocalDateTime createdDateAt) {
        this.createdDateAt = createdDateAt;
    }
}
