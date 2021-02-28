package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestConfig {

    /**
     * We need to declare WebClient as a Spring bean because
     * Spring Sleuth needs the WebClient to add the tracing information via headers in the request.
     * Spring Sleuth adds tracing information to log statements.
     *
     *  To log HTTP request headers, set log level to trace for ExchangeFunctions:
     *  logging.level.org.springframework.web.reactive.function.client.ExchangeFunctions=TRACE
     */
    @Bean
    public WebClient webClient() {

        return WebClient
                .builder()
                .exchangeStrategies( // Explicitly enable header logging. By default, headers are masked.
                        ExchangeStrategies.builder().codecs(c -> c.defaultCodecs().enableLoggingRequestDetails(true)).build())
                .build();
    }
}
