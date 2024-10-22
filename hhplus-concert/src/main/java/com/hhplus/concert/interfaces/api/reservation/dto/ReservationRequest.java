package com.hhplus.concert.interfaces.api.reservation.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private UUID userId;
    private Long seatId;
    private Long seatAmount;
    private Integer seatPosition;
}
