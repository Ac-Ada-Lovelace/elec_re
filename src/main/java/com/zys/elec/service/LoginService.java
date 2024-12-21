package com.zys.elec.service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.LoginDTO;
import com.zys.elec.dto.UserDTO;

public interface LoginService {

    public ServiceResult<UserDTO> login(LoginDTO user);

}
