package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dto.PaymentRequestDto;
import com.dto.PaymentResponseDto;
import com.dto.ProductDto;
import com.service.PaymentService;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/products/balance")
    public ResponseEntity<List<ProductDto>> getProductsBalances(@RequestParam Long userId) {
        List<ProductDto> products = paymentService.getProductsWithBalances(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}/balance")
    public ResponseEntity<Map<String, Object>> getProductBalance(@PathVariable Long productId) {
        BigDecimal balance = paymentService.getProductBalance(productId);
        return ResponseEntity.ok(Map.of("productId", productId, "balance", balance));
    }

    @PutMapping("/products/{productId}/balance")
    public ResponseEntity<ProductDto> updateBalance(
            @PathVariable Long productId,
            @RequestParam Long userId,
            @RequestParam BigDecimal amount) {
        ProductDto updated = paymentService.updateBalance(productId, userId, amount);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/execute")
    public ResponseEntity<PaymentResponseDto> executePayment(@RequestBody PaymentRequestDto request) {
        PaymentResponseDto response = paymentService.executePayment(request);
        return ResponseEntity.ok(response);
    }

}