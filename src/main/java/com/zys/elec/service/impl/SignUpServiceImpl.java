package com.zys.elec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.User;
import com.zys.elec.service.SignUpService;
import com.zys.elec.service.UserService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserService userService;



    @Override
    public ServiceResult<UserDTO> signUp(User user) {
        var res = userService.create(user);
        if (res.isSuccess()) {
            return ServiceResult.success(res.getData());
        } else {
            return ServiceResult.failure(res.getMessage());

        }
    }

}
