package com.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.client.ProductClient;
import com.dto.PaymentRequestDto;
import com.dto.PaymentResponseDto;
import com.dto.BalanceDto;
import com.dto.ProductDto;
import com.exception.MyException;

@Service
public class PaymentService {

    private final ProductClient httpClient;

    public PaymentService(ProductClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<ProductDto> getProductsWithBalances(Long userId) {
        Objects.requireNonNull(userId, "userId не может быть пустым");
        return httpClient.getProductsByUserId(userId);
    }


    public BalanceDto getProductBalance(Long productId) {
        Objects.requireNonNull(productId, "productId не может быть пустым");
        ProductDto productDto = httpClient.getProductById(productId);
        return new BalanceDto(productId,productDto.balance());
    }

    public ProductDto updateBalance(Long productId, Long userId, BigDecimal amount) {
        Objects.requireNonNull(productId, "productId не может быть пустым");
        Objects.requireNonNull(userId, "userId не может быть пустым");
        Objects.requireNonNull(amount, "amount не может быть пустым");
        ProductDto product = httpClient.getProductById(productId);

        if (!product.userId().equals(userId)) {
            throw new MyException(HttpStatus.FORBIDDEN,
                    "Продукт не принадлежит пользователю " + userId);
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return httpClient.decBalance(productId, amount.abs());
        } else {
            return httpClient.incBalance(productId, amount);
        }
    }

    public PaymentResponseDto executePayment(PaymentRequestDto request) {
        Objects.requireNonNull(request, "PaymentRequest не может быть пустым");
        BigDecimal amountToDeduct = request.amount().abs().negate();
        ProductDto updated = updateBalance(request.productId(), request.userId(), amountToDeduct);
        return new PaymentResponseDto(true, "Платёж выполнен успешно", updated);
    }
}