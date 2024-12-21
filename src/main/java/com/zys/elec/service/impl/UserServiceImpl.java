package com.zys.elec.service.impl;

import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.User;
import com.zys.elec.repository.UserRepository;
import com.zys.elec.service.UserService;
import com.zys.elec.common.ServiceResult;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// change convertToDTO method to convertToDTO method in UserDTO class
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserDTO convertToDTO(User user) {
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    @Override
    public ServiceResult<UserDTO> create(User user) {
        var exists = userRepository.findByUsername(user.getUsername());
        if (exists.isPresent()) {
            return ServiceResult.failure("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            var newUser = userRepository.save(user);
            return ServiceResult.success(convertToDTO(newUser));
        } catch (Exception e) {
            return ServiceResult.failure("Failed to create user");
        }
    }

    @Override
    public ServiceResult<UserDTO> update(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            var updatedUser = new User();
            try {
                updatedUser = userRepository.save(user);
            } catch (Exception e) {
                return ServiceResult.failure("Failed to update user");
            }
            return ServiceResult.success(convertToDTO(updatedUser));
        } else {
            return ServiceResult.failure("User not found");
        }
    }

    @Override
    public ServiceResult<UserDTO> delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return ServiceResult.success(null);
        } else {
            return ServiceResult.failure("User not found");
        }
    }

    @Override
    public ServiceResult<UserDTO> getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return ServiceResult.success(convertToDTO(user.get()));
        } else {
            return ServiceResult.failure("User not found");
        }
    }

    @Override
    public ServiceResult<UserDTO> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ServiceResult.success(convertToDTO(user.get()));
        } else {
            return ServiceResult.failure("User not found");
        }
    }

    @Override
    public ServiceResult<UserDTO> checkPassword(String username, String password) {
        var res = userRepository.findByUsername(username);
        if (res.isPresent()) {
            var user = res.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return ServiceResult.success(convertToDTO(user));
            } else {
                return ServiceResult.failure("Incorrect password");
            }
        } else {
            return ServiceResult.failure("User not found");
        }

    }
}