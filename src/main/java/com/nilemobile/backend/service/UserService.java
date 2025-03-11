package com.nilemobile.backend.service;

import com.nilemobile.backend.model.User;

public interface UserService {
    public User findUserById(Long userID) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
