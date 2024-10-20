package com.hhplus.concert.interfaces.api.concert.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertRequest {
    private Long concertId;
    private String title;
}
