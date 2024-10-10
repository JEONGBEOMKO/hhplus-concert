package com.hhplus.concert.interfaces.api.reservation;

import com.hhplus.concert.interfaces.api.reservation.dto.AvailableSeats;
import com.hhplus.concert.interfaces.api.reservation.dto.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    // 예약 가능 날짜 조회 API
    @GetMapping("/available-dates")
    public ResponseEntity<List<String>> getAvailableDates() {
        // 더미 값으로 반환할 예약 가능 날짜
        List<String> availableDates = Arrays.asList("2024-10-15", "2024-10-16", "2024-10-17");
        return ResponseEntity.ok(availableDates);
    }

    // 예약 가능 좌석 조회 API
    @GetMapping("/available-seats")
    public ResponseEntity<AvailableSeats> getAvailableSeats(@RequestParam String date) {
        // 더미 값으로 반환할 예약 가능 좌석
        List<String> availableSeats = Arrays.asList("좌석 1", "좌석 2", "좌석 3");
        AvailableSeats availableSeatsDto = new AvailableSeats(date, availableSeats);
        return ResponseEntity.ok(availableSeatsDto);
    }


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
