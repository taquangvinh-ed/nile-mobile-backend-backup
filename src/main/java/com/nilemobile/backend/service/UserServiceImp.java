package com.nilemobile.backend.service;

import com.nilemobile.backend.config.JwtProvider;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.UserProfileDTO;
import com.nilemobile.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    public UserServiceImp(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public User findUserById(Long userID) throws UserException {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with user_id: " + userID);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String phoneNumber = jwtProvider.getPhoneNumberfromToken(jwt);

        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new UserException("User not found with email: " + phoneNumber);
        }
        return user;
    }

    @Override
    public UserProfileDTO updateProfile(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            User updatedUser =  userRepository.save(existingUser);
            return new UserProfileDTO(
                    updatedUser.getUserId(),
                    updatedUser.getFirstName(),
                    updatedUser.getLastName(),
                    updatedUser.getEmail(),
                    updatedUser.getPhoneNumber()
            );
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}
