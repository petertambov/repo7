package com.config;

import com.config.properties.ExternalClientsProperties;
import com.config.properties.RestTemplateProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.client.ProductExceptionHandler;


@Configuration
@EnableConfigurationProperties(ExternalClientsProperties.class)
public class IntegrationsConfig {

    private final ExternalClientsProperties restProperties;

    public IntegrationsConfig(ExternalClientsProperties clientsProperties) {
        this.restProperties = clientsProperties;
    }

    @Bean
    public RestTemplate productClient(ProductExceptionHandler errorHandler) {
        RestTemplateProperties restClient = restProperties.getProductClient();
        return new RestTemplateBuilder()
                .rootUri(restClient.url())
                .setConnectTimeout(restClient.connectTimeout())
                .setReadTimeout(restClient.readTimeout())
                .errorHandler(errorHandler)
                .build();
    }

}
