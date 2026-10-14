package com.client;

import com.exception.MyException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class HttpExceptionHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public HttpExceptionHandler(ObjectMapper objectMapper) {
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
        String message = body; // fallback

        try {
            JsonNode node = objectMapper.readTree(body);
            if (node.has("message")) {
                message = node.get("message").asText();
            }
        } catch (Exception ignored) {
        }

        throw new MyException(status, message);
    }
}