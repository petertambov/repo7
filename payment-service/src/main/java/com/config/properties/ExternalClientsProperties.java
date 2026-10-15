package com.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations.clients")
public class ExternalClientsProperties {

    private RestTemplateProperties productClient;

    public void setProductClient(RestTemplateProperties productClient) {
        this.productClient = productClient;
    }

    public RestTemplateProperties getProductClient() {
        return productClient;
    }
}

