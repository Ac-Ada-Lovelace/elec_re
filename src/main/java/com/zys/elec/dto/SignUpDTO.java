package com.zys.elec.dto;

import com.zys.elec.entity.User;

import lombok.Data;

@Data
public class SignUpDTO {
    String username;
    String password;


    public User ToUser(){
        var user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }
}
