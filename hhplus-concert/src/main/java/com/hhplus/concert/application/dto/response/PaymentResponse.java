package com.hhplus.concert.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private UUID userId;
    private Long seatId;
    private Long price;
    private String status;
    private LocalDateTime createdAt;

    public PaymentResponse(Long paymentId, UUID userId, Long seatId, Long price, String status, LocalDateTime createdAt) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.seatId = seatId;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }
}
