package com.example.auth.service;

import com.example.common.jwt.JwtUser;
import com.example.common.jwt.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtGenerator jwtGenerator;

    public AuthService(
            final AuthenticationManager authenticationManager,
            final JwtUserDetailsService jwtUserDetailsService,
            final JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtGenerator = jwtGenerator;
    }

    public String generateJwtToken(String userName){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userName));
        JwtUser user = jwtUserDetailsService.loadUserByUsername(userName);
        return jwtGenerator.generateToken(user);
    }
}
