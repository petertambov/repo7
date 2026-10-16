package com.service;

import com.dto.ProductDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductService {

    private final RestTemplate restTemplate;

    private static final String PRODUCTS_PATH = "/v1/products";
    private static final String BY_ID_PATH = PRODUCTS_PATH + "/{id}";
    private static final String DEC_PATH = BY_ID_PATH + "/dec";
    private static final String INC_PATH = BY_ID_PATH + "/inc";
    private static final String USER_ID_PARAM = "userId";
    private static final String AMOUNT_PARAM = "amount";

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductDto> getProductsByUserId(Long userId) {
        String uri = UriComponentsBuilder.fromPath(PRODUCTS_PATH)
                .queryParam(USER_ID_PARAM, userId)
                .build()
                .toUriString();

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );
        return response.getBody();
    }

    public ProductDto getProductById(Long productId) {
        String uri = UriComponentsBuilder.fromPath(BY_ID_PATH)
                .buildAndExpand(productId)
                .toUriString();
        return restTemplate.getForEntity(uri, ProductDto.class).getBody();
    }

    public ProductDto decBalance(Long productId, BigDecimal amount) {
        String uri = UriComponentsBuilder.fromPath(DEC_PATH)
                .queryParam(AMOUNT_PARAM, amount)
                .buildAndExpand(productId)
                .toUriString();
        return restTemplate.exchange(uri, HttpMethod.PUT, HttpEntity.EMPTY, ProductDto.class).getBody();
    }

    public ProductDto incBalance(Long productId, BigDecimal amount) {
        String uri = UriComponentsBuilder.fromPath(INC_PATH)
                .queryParam(AMOUNT_PARAM, amount)
                .buildAndExpand(productId)
                .toUriString();
        return restTemplate.exchange(uri, HttpMethod.PUT, HttpEntity.EMPTY, ProductDto.class).getBody();
    }
}
