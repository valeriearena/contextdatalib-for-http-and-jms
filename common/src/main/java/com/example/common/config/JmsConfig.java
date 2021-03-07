package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 * Registers MessageConverter as a Spring Bean so com.mobileheartbeat.banyan.common.jms.CommonJmsInterceptor
 * can intercept calls to MessageConverter::toMessage (Spring aspects only intercept Spring beans).
 */
@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new SimpleMessageConverter();
    }

}