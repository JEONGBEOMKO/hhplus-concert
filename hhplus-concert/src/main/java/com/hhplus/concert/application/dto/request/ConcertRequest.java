package com.hhplus.concert.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConcertRequest {
    private Long concertId;

    public ConcertRequest(Long concertId) {
        this.concertId = concertId;
    }
}
