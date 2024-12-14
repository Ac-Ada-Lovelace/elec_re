package com.zys.elec.service.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.zys.elec.service.JWTAuthService;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JWTAuthServiceImpl implements JWTAuthService {
    private static final String SECRET_KEY = "your_secret_key";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    @Override
    public String generateToken(String username, String captcha) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(username)
                .withClaim("captcha", captcha)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }
}