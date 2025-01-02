package com.zys.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.entity.User;
import com.zys.elec.repository.UserRepository;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("add")
    public ResponseResult<String> add() {
        var user = new User();
        user.setUsername("testfordatabase");
        user.setPassword("testdatabase");

        userRepository.save(user);
        return ResponseResult.success("add success");
    }
}
