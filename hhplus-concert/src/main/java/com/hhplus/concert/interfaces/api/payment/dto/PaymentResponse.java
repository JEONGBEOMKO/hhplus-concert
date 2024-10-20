package com.hhplus.concert.interfaces.api.payment.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private UUID userId;
    private Long seatId;
    private Long price;
    private String status;
    private LocalDateTime createdAt;
}
