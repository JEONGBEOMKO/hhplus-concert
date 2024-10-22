package com.hhplus.concert.interfaces.api.concertschedule.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertScheduleResponse {
    private Long concertScheduleId;
    private Long concertId;
    private LocalDate openAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int totalSeat;
    private int availableSeat;
    private String totalSeatStatus;
}
