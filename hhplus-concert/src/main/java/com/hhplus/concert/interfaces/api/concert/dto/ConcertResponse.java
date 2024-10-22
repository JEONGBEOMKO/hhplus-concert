package com.hhplus.concert.interfaces.api.concert.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertResponse {
    private Long concertId;
    private String title;
    private LocalDateTime createdAt;
}
