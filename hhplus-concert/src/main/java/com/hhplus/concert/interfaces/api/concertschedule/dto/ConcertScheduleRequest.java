package com.hhplus.concert.interfaces.api.concertschedule.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertScheduleRequest {
    private Long concertId;
    private LocalDate openAt;
}
