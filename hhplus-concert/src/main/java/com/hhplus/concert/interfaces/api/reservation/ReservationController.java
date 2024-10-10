package com.hhplus.concert.interfaces.api.reservation;

import com.hhplus.concert.interfaces.api.reservation.dto.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    // 좌석 예약 요청 API
    @PostMapping("/users/{userId}/reserve")
    public ResponseEntity<Reservation> reserveSeat(@PathVariable Long userId, @RequestBody Reservation reservationRequest){
        Reservation reservation = new Reservation(
                1L,
                userId,
                reservationRequest.getSeatId(),
                LocalDate.now(),
                LocalDateTime.now().plusMinutes(5) // 임시 예약 만료 시간
        );

        return ResponseEntity.ok(reservation);
    }

    // 예약 정보 조회 API
    @GetMapping("/users/{userId}/reservations/{resId}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long userId, @PathVariable Long resId){
        Reservation reservation = new Reservation(
                resId,
                userId,
                "좌석 1",
                LocalDate.now(),
                LocalDateTime.now().plusMinutes(5) //임시 예약 만료 시간
        );

        return ResponseEntity.ok(reservation);
    }
}
