package com.nilemobile.backend.service;

import com.nilemobile.backend.config.JwtProvider;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.UserProfileDTO;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.request.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
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

            User updatedUser = userRepository.save(existingUser);
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
    public User updateProfileUser(Long userId, UserProfileDTO userProfileDTO) throws UserException {
        if(userProfileDTO.getFirstName()==null || userProfileDTO.getFirstName().isEmpty() ||
                userProfileDTO.getLastName()==null || userProfileDTO.getLastName().isEmpty() ||
                userProfileDTO.getEmail()==null || userProfileDTO.getEmail().isEmpty() ||
                userProfileDTO.getPhoneNumber()==null || userProfileDTO.getPhoneNumber().isEmpty()){
            throw new UserException("User profile information is incomplete");
        }
        User user = findUserById(userId);
        user.setFirstName(userProfileDTO.getFirstName());
        user.setLastName(userProfileDTO.getLastName());
        user.setEmail(userProfileDTO.getEmail());
        user.setPhoneNumber(userProfileDTO.getPhoneNumber());
        user.setCreatedDateAt(user.getCreatedDateAt());
        return userRepository.save(user);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void changePassword(String phoneNumber, ChangePasswordRequest request) throws Exception {
        // Tìm user theo phoneNumber
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new Exception("User not found with phone number: " + phoneNumber);
        }

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new Exception("Old password is incorrect");
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp không
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new Exception("New password and confirm password do not match");
        }

        // Kiểm tra mật khẩu mới có hợp lệ không (ví dụ: độ dài tối thiểu)
        if (request.getNewPassword().length() < 8) {
            throw new Exception("New password must be at least 8 characters long");
        }

        // Mã hóa mật khẩu mới và cập nhật vào cơ sở dữ liệu
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
