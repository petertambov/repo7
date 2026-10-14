package com.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import com.dto.ProductDto;

@Component
public class HttpClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    private final String PRODUCTS_PATH = "/v1/products";
    private final String BY_ID_PATH = PRODUCTS_PATH + "/{id}";
    private final String DEC_PATH = BY_ID_PATH + "/dec";
    private final String INC_PATH = BY_ID_PATH + "/inc";
    private final String USER_ID_PARAM = "userId";
    private final String AMOUNT_PARAM = "amount";

    public HttpClient(RestTemplate restTemplate,
                                @Value("${product.service.url}") String productServiceUrl) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    public List<ProductDto> getProductsByUserId(Long userId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(productServiceUrl)
                .path(PRODUCTS_PATH)
                .queryParam(USER_ID_PARAM, userId)
                .build()
                .toUri();
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );
        return response.getBody();
    }

    public ProductDto getProductById(Long productId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(productServiceUrl)
                .path(BY_ID_PATH)
                .buildAndExpand(productId)
                .toUri();
        ResponseEntity<ProductDto> response = restTemplate.getForEntity(uri, ProductDto.class);
        return response.getBody();
    }

    public ProductDto decBalance(Long productId, BigDecimal amount) {
        URI uri = UriComponentsBuilder.fromHttpUrl(productServiceUrl)
                .path(DEC_PATH)
                .queryParam(AMOUNT_PARAM, amount)
                .buildAndExpand(productId)
                .toUri();
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                HttpEntity.EMPTY,
                ProductDto.class
        );
        return response.getBody();
    }

    public ProductDto incBalance(Long productId, BigDecimal amount) {
        URI uri = UriComponentsBuilder.fromHttpUrl(productServiceUrl)
                .path(INC_PATH)
                .queryParam(AMOUNT_PARAM, amount)
                .buildAndExpand(productId)
                .toUri();
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                HttpEntity.EMPTY,
                ProductDto.class
        );
        return response.getBody();
    }
}