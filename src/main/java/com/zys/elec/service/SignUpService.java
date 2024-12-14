package com.zys.elec.service;

import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.User;

@Service
public interface SignUpService {

    ServiceResult<UserDTO> signUp(User user);
}
