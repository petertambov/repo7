package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.client.ProductExceptionHandler;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(
            ProductExceptionHandler errorHandler,
            @Value("${rest.timeout.connect:5000}") int connectTimeout,
            @Value("${rest.timeout.read:10000}") int readTimeout) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(errorHandler);

        return restTemplate;
    }
}