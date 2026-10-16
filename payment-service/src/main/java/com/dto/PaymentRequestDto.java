package com.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(Long userId, Long productId, BigDecimal amount){}
