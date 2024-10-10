package com.hhplus.concert.interfaces.api.balance.dto;

import java.math.BigDecimal;

public class Balance {
    private Long userId;
    private BigDecimal balance;

    public Balance(Long userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
