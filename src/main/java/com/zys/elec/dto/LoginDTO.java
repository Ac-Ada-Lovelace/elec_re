package com.zys.elec.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;

    private String captcha;



}
