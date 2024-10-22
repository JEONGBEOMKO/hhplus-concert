package com.hhplus.concert.interfaces.api.seat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private Long id;
    private Long concertScheduleId;
    private Long amount;
    private Integer position;
    private String seatStatus;
}
