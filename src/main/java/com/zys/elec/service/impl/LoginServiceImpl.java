package com.zys.elec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.LoginDTO;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.service.LoginService;
import com.zys.elec.service.UserService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Override
    public ServiceResult<UserDTO> login(LoginDTO user) {
        // var un = user.getUsername();
        // var pw = user.getPassword();
        var res = userService.checkPassword(user.getUsername(), user.getPassword());
        if (res.isSuccess()) {
            return ServiceResult.success(res.getData());
        } else {
            return ServiceResult.failure(res.getMessage());
        }
    }

}
