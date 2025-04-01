package com.nilemobile.backend.service;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.UserProfileDTO;
import com.nilemobile.backend.request.ChangePasswordRequest;

public interface UserService {
    public User findUserById(Long userID) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public UserProfileDTO updateProfile(Long userId, User user);

    public User findByPhoneNumber(String phoneNumber);

    void changePassword(String phoneNumber, ChangePasswordRequest request) throws Exception;
}
