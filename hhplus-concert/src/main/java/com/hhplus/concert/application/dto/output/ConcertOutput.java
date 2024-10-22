package com.hhplus.concert.application.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertOutput {
    private Long concertId;
    private String title;
    private LocalDateTime createdAt;
}
