package com.hhplus.concert.interfaces.api.concertschedule.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertScheduleRequest {
    private Long concertId;
    private String date;
}
