package com.example.common.context;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
public class ExampleContextData {
    private String jwtToken;
    private String userName;
}
