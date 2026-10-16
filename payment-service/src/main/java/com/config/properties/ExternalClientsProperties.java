package com.config.properties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations.clients")
public class ExternalClientsProperties {

    private final RestTemplateProperties productClient;

    public ExternalClientsProperties(RestTemplateProperties productClient) {
        this.productClient = productClient;
    }

    public RestTemplateProperties getProductClient() {
        return productClient;
    }

    /*public RestTemplateProperties getWeatherClient() {
        return weatherClient;
    }*/

}

