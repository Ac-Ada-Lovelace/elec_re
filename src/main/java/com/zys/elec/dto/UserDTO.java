package com.zys.elec.dto;

import java.time.LocalDateTime;

import com.zys.elec.entity.User;

import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    private String username;
    private byte[] avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String jwt;
    public static UserDTO fromEntity(User user) {
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    public User toEntity() {
        var user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setAvatar(this.avatar);
        user.setCreatedAt(this.createdAt);
        user.setUpdatedAt(this.updatedAt);
        return user;
    }
    // getters and setters
}