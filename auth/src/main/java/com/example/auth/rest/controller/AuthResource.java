package com.example.auth.rest.controller;

import com.example.auth.rest.data.AuthRequest;
import com.example.auth.rest.data.AuthResponse;
import com.example.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthResource{

    private final AuthService authService;

    public AuthResource(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse auth(@RequestBody AuthRequest authRequest){
        String token = authService.generateJwtToken(authRequest.getUsername());
        return new AuthResponse(token);
    }
}
