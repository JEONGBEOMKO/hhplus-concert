package com.hhplus.concert.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConcertResponse {
    private Long concertId;
    private String title;

    public ConcertResponse(Long concertId, String title) {
        this.concertId = concertId;
        this.title = title;
    }
}
