package com.example.auth.rest.data;

import lombok.Data;

@Data
public class AuthResponse {
    private final String jwtToken;
}
