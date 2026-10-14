package com.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.client.HttpExceptionHandler;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(HttpExceptionHandler errorHandler) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }
}