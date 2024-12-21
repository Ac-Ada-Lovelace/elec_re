package com.zys.elec.service;


import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.User;

@Service
public interface UserService {
    
    public ServiceResult<UserDTO> getUserById(Long id);

    public ServiceResult<UserDTO> getByUsername(String username);

    public ServiceResult<UserDTO> create(User user);

    public ServiceResult<UserDTO> update(User user);

    public ServiceResult<UserDTO> delete(Long id);

    public ServiceResult<UserDTO> checkPassword(String username, String password);

}