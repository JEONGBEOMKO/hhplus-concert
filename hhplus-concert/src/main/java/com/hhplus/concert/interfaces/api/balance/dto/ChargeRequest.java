package com.hhplus.concert.interfaces.api.balance.dto;

import java.math.BigDecimal;

public class ChargeRequest {
    Long userId;
    private BigDecimal amount;

    public ChargeRequest() {
    }

    public ChargeRequest(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
