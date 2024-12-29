package com.zys.elec.service;


public interface  JWTAuthService {
    public String generateToken(String username, String userId, String captcha);
    public boolean validateToken(String token);
    public String getUsernameFromToken(String token);
    public String getUserIdFromToken(String token);
}


