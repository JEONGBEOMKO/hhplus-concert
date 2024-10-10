package com.hhplus.concert.interfaces.api.payment.dto;

import java.math.BigDecimal;

public class Payment {
    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private BigDecimal amount;

    public Payment(Long paymentId, Long userId, Long reservationId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.reservationId = reservationId;
        this.amount = amount;
    }
}
