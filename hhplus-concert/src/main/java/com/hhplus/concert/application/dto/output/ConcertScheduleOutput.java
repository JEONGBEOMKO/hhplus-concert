package com.hhplus.concert.application.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ConcertScheduleOutput {
    private Long concertScheduleId;
    private Long concertId;
    private LocalDate openAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int totalSeat;
    private int availableSeat;
    private String totalSeatStatus;
}
