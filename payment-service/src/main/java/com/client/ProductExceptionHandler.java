package com.client;

import com.exception.MyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.dto.ErrorResponseDto;

@Component
public class ProductExceptionHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public ProductExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus status = HttpStatus.valueOf(response.getStatusCode().value());
        String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

        String message = "Неизвестная ошибка";
        try {
            ErrorResponseDto dto = objectMapper.readValue(body, ErrorResponseDto.class);
            if (dto.message() != null && !dto.message().isBlank()) {
                message = dto.message();
            }
        } catch (Exception ignored) {
            message = "Внутренняя ошибка обработки ответа";
        }

        throw new MyException(status, message);
    }
}