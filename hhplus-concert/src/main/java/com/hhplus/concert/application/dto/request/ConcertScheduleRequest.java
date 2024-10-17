package com.hhplus.concert.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConcertScheduleRequest {
    private Long concertId;
    private String date; // 날짜를 받아서 해당 날짜의 스케줄 조회

    public ConcertScheduleRequest(Long concertId, String date) {
        this.concertId = concertId;
        this.date = date;
    }
}
