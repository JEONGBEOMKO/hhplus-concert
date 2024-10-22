package com.hhplus.concert.interfaces.api.seat.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {
    private Long concertScheduleId;
}
