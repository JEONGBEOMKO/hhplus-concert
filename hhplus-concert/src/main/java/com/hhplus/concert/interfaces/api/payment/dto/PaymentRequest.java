package com.hhplus.concert.interfaces.api.payment.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private UUID userId;
    private Long seatId;
    private Long amount;
}
