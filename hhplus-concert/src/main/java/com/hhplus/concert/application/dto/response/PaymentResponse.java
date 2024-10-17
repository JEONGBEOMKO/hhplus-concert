package com.hhplus.concert.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {
    private UUID userId;
    private Long seatId;
    private Long amount;
    private String status;

    public PaymentResponse(UUID userId, Long seatId, Long amount, String status){
        this.userId = userId;
        this.seatId = seatId;
        this.amount = amount;
        this.status = status;
    }
}
