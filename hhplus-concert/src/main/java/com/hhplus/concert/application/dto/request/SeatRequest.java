package com.hhplus.concert.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SeatRequest {
    private Long concertScheduleId;

    public SeatRequest(Long concertScheduleId) {
        this.concertScheduleId = concertScheduleId;
    }
}
