package com.hhplus.concert.interfaces.api.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class Reservation {
    private Long resId;
    private Long userId;
    private String seatId;
    private LocalDate date;
    private LocalDateTime expirationTime;

    public Reservation(Long resId, Long userId, String seatId, LocalDate date, LocalDateTime expirationTime) {
        this.resId = resId;
        this.userId = userId;
        this.seatId = seatId;
        this.date = date;
        this.expirationTime = expirationTime;
    }
}
