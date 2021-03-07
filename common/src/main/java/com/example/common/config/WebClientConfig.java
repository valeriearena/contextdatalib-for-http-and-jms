package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .exchangeStrategies( // Explicitly enable header logging. By default, headers are masked.
                        ExchangeStrategies.builder().codecs(c -> c.defaultCodecs().enableLoggingRequestDetails(true)).build())
                .build();
    }

}
