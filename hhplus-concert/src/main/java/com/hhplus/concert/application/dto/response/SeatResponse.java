package com.hhplus.concert.application.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeatResponse {
    private Long id;
    private Long concertScheduleId;
    private Long amount;
    private Integer position;
    private String seatStatus;

    public SeatResponse(Long id, Long concertScheduleId, Long amount, Integer position, String seatStatus) {
        this.id = id;
        this.concertScheduleId = concertScheduleId;
        this.amount = amount;
        this.position = position;
        this.seatStatus = seatStatus;
    }
}
