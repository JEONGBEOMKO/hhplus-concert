package com.hhplus.concert.application.dto.input;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInput {
    private UUID userId;
    private Long seatId;
    private Long seatAmount;
    private Integer seatPosition;
}
