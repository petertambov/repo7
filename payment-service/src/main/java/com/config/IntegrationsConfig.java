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

    private final ExternalClientsProperties clientsProperties;

    public IntegrationsConfig(ExternalClientsProperties clientsProperties) {
        this.clientsProperties = clientsProperties;
    }


    @Bean
    public RestTemplate paymentClient(ProductExceptionHandler errorHandler) {
        RestTemplateProperties paymentsClient = clientsProperties.getProductClient();
        return new RestTemplateBuilder()
                .rootUri(paymentsClient.url())
                .setConnectTimeout(paymentsClient.connectTimeout())
                .setReadTimeout(paymentsClient.readTimeout())
                .errorHandler(errorHandler)
                .build();
    }

}
