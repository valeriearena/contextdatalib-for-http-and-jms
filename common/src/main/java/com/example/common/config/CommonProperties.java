package com.example.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "common")
@PropertySource({"classpath:application-common.properties"})
public class CommonProperties {
    private String jwtSecret;
}
