package com.controller;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


import com.dto.PaymentRequestDto;
import com.dto.PaymentResponseDto;
import com.dto.ProductDto;
import com.dto.BalanceDto;
import com.service.PaymentService;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/products/balance")
    public List<ProductDto> getProductsBalances(@RequestParam Long userId) {
        return paymentService.getProductsWithBalances(userId);
    }

    @GetMapping("/products/{productId}")
    public BalanceDto getProductBalance(@PathVariable Long productId) {
        return paymentService.getProductBalance(productId);
    }

    @PutMapping("/products/{productId}/balance")
    public ProductDto updateBalance(
            @PathVariable Long productId,
            @RequestParam Long userId,
            @RequestParam BigDecimal amount) {
        return paymentService.updateBalance(productId, userId, amount);
    }

    @PostMapping("/execute")
    public PaymentResponseDto executePayment(@RequestBody PaymentRequestDto request) {
        return paymentService.executePayment(request);

    }

}