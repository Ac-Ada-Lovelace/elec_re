package com.zys.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.LoginDTO;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.service.LoginService;

import io.micrometer.common.lang.NonNull;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public ResponseResult<UserDTO> Login(@NonNull @RequestBody LoginDTO user) {

        // if (user.getUsername() == null || user.getUsername().length() < 6) {
        //     return ResponseResult.failure("Username is too short");
        // }
        // if (user.getPassword() == null || user.getPassword().length() < 6) {
        //     return ResponseResult.failure("Password is too short");
        // }

        var res = loginService.login(user);
        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());
        }

    }
}
