package com.zys.elec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.SignUpDTO;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.service.SignUpService;

import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sign-up")
@CrossOrigin(origins = "http://localhost:9528")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/")
    public ResponseResult<UserDTO> SignUp(@RequestBody @NonNull SignUpDTO user) {
        // if (user.getPassword() == null || user.getPassword().length() < 6) {
        //     return ResponseResult.failure("Password is too short");
        // }
        // if (user.getUsername() == null || user.getUsername().length() < 6) {
        //     return ResponseResult.failure("Username is too short");
        // }

        var _user = user.ToUser();

        var res = signUpService.signUp(_user);
        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());
        }
    }
}