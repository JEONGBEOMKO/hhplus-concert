package com.hhplus.concert.interfaces.api.reservation.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long reservationId;
    private UUID userId;
    private Long seatId;
    private Long seatAmount;
    private Integer seatPosition;
    private String status;
    private LocalDateTime reservedAt;
    private LocalDateTime reservedExpiresAt;
}
