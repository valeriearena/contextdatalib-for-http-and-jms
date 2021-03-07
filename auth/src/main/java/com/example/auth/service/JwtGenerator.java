package com.example.auth.service;

import com.example.common.config.CommonProperties;
import com.example.common.jwt.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtGenerator {

    private final CommonProperties commonProperties;

    public JwtGenerator(final CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    public String generateToken(JwtUser username) {
        Claims claims = Jwts.claims().setSubject(username.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, commonProperties.getJwtSecret())
                .compact();
    }
}
