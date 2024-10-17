package com.hhplus.concert.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ConcertScheduleResponse {
    private Long concertScheduleId;
    private Long concertId;
    private LocalDate openAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int totalSeat;
    private int availableSeat;
    private String totalSeatStatus;

    public ConcertScheduleResponse(Long concertScheduleId, Long concertId, LocalDate openAt, LocalDateTime startAt,
                                   LocalDateTime endAt, int totalSeat, int availableSeat, String totalSeatStatus) {
        this.concertScheduleId = concertScheduleId;
        this.concertId = concertId;
        this.openAt = openAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.totalSeat = totalSeat;
        this.availableSeat = availableSeat;
        this.totalSeatStatus = totalSeatStatus;
    }
}
