package com.hhplus.concert.application.dto.output;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOutput {
    private Long paymentId;
    private UUID userId;
    private Long seatId;
    private Long price;
    private String status;
    private LocalDateTime createdAt;
}
