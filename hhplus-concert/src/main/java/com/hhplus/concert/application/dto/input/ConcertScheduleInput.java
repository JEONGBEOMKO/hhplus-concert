package com.hhplus.concert.application.dto.input;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertScheduleInput {
    private Long concertId;
    private LocalDate openAt;
}
