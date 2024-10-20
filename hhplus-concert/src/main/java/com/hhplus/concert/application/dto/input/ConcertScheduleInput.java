package com.hhplus.concert.application.dto.input;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertScheduleInput {
    private Long concertId;
    private String date;
}
