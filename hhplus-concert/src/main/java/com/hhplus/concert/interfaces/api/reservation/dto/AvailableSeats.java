package com.hhplus.concert.interfaces.api.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class AvailableSeats {
    private String date;
    private List<String> availableSeats;

    public AvailableSeats(String date, List<String> availableSeats) {
        this.date = date;
        this.availableSeats = availableSeats;
    }
}
