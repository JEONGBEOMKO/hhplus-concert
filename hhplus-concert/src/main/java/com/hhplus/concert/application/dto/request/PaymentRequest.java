package com.hhplus.concert.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {
    private UUID userId;
    private Long seatId;

    public PaymentRequest(UUID userId, Long seatId) {
        this.userId = userId;
        this.seatId = seatId;
    }
}
