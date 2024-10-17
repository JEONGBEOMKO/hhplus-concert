package com.hhplus.concert.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {

    private UUID userId;
    private Long seatId;
    private Long seatAmount;
    private Integer seatPosition;

    public ReservationRequest(UUID userId, Long seatId, Long seatAmount, Integer seatPosition){
        this.userId = userId;
        this.seatId = seatId;
        this.seatAmount = seatAmount;
        this.seatPosition = seatPosition;
    }


}
