package com.hhplus.concert.application.dto.output;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatOutput {
    private Long id;
    private Long concertScheduleId;
    private Long amount;
    private Integer position;
    private String seatStatus;
}
