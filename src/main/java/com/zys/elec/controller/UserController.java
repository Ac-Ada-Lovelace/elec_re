package com.zys.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.entity.User;
import com.zys.elec.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseResult<User> getUserById(@RequestParam Long id) {
        var result = userService.getUserById(id);
        if (result.isSuccess()) {
            return ResponseResult.success(result.getData().toEntity());
        } else {
            return ResponseResult.failure(result.getMessage());
        }

    }

}
