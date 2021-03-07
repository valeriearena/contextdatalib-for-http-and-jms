package com.example.common.jwt;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtParser {

    private static final String USERNAME_CLAIM = "username";

    public String getUsername(Claims claims) {
        return (String) claims.get(USERNAME_CLAIM);
    }
}
