package com.zys.elec.dto;

import java.time.LocalDateTime;

import com.zys.elec.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    private String username;
    private byte[] avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;

    public static UserDTO fromEntity(User user) {
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setDeleted(user.isDeleted());
        return userDTO;
    }

    public User toEntity() {
        var user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setAvatar(this.avatar);
        user.setCreatedAt(this.createdAt);
        user.setUpdatedAt(this.updatedAt);
        user.setDeleted(this.isDeleted);
        return user;
    }
    // getters and setters
}