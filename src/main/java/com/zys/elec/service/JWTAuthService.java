package com.zys.elec.service;


public interface  JWTAuthService {
    public String generateToken(String username, String captcha);
    public boolean validateToken(String token);
    public String getUsernameFromToken(String token);
}


