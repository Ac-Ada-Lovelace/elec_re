package com.zys.elec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.User;
import com.zys.elec.service.SignUpService;

import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sign-up")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/")
    public ResponseResult<UserDTO> Login(@RequestBody @NonNull User user) {
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return ResponseResult.failure("Password is too short");
        }
        if (user.getUsername() == null || user.getUsername().length() < 6) {
            return ResponseResult.failure("Username is too short");
        }
        
        var _user = new User();
        _user.setUsername(user.getUsername());
        _user.setPassword(user.getPassword());

        var res = signUpService.signUp(_user);
        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());
        }
    }
}