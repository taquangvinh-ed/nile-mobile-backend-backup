package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserProfileDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String createdDateAt;

    public UserProfileDTO() {}

    public UserProfileDTO(User user){
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.createdDateAt = user.getCreatedDateAt().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
    }

    public UserProfileDTO(Long userId, String firstName, String lastName, String email, String phoneNumber, String createdDateAt) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdDateAt = createdDateAt;
    }

    public UserProfileDTO(Long userId, String firstName, String lastName, String email, String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreatedDateAt() {
        return createdDateAt;
    }

    public void setCreatedDateAt(String createdDateAt) {
        this.createdDateAt = createdDateAt;
    }
}
