package com.hhplus.concert.interfaces.api.reservation;

import com.hhplus.concert.application.dto.request.ConcertScheduleRequest;
import com.hhplus.concert.application.dto.request.ReservationRequest;
import com.hhplus.concert.application.dto.response.ConcertScheduleResponse;
import com.hhplus.concert.application.dto.response.ReservationResponse;
import com.hhplus.concert.application.dto.response.SeatResponse;
import com.hhplus.concert.application.usecase.GetAvailableDatesUseCase;
import com.hhplus.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.concert.application.usecase.ReserveSeatUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReserveSeatUseCase reserveSeatUseCase;
    private final GetAvailableDatesUseCase getAvailableDatesUseCase;
    private final GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    public ReservationController(ReserveSeatUseCase reserveSeatUseCase, GetAvailableDatesUseCase getAvailableDatesUseCase, GetAvailableSeatsUseCase getAvailableSeatsUseCase) {
        this.reserveSeatUseCase = reserveSeatUseCase;
        this.getAvailableDatesUseCase = getAvailableDatesUseCase;
        this.getAvailableSeatsUseCase = getAvailableSeatsUseCase;
    }

    // 예약 가능 날짜 조회 API
    @GetMapping("/available-dates")
    public ResponseEntity<List<ConcertScheduleResponse>> getAvailableDates(@RequestParam Long concertId) {
        ConcertScheduleRequest request = new ConcertScheduleRequest(concertId, null); // 일단 날짜 없이 concertId만 넘김
        List<ConcertScheduleResponse> availableDates = getAvailableDatesUseCase.getAvailableDates(request);
        return ResponseEntity.ok(availableDates);
    }

    // 예약 가능 좌석 조회 API
    @GetMapping("/available-seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@RequestParam Long concertScheduleId) {
        List<SeatResponse> availableSeats = getAvailableSeatsUseCase.getAvailableSeats(concertScheduleId);
        return ResponseEntity.ok(availableSeats);
    }



    // 좌석 예약 API
    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponse> reserveSeat(@RequestBody ReservationRequest request) {
        ReservationResponse response = reserveSeatUseCase.reserveSeat(request);
        return ResponseEntity.ok(response);
    }

    // 예약된 좌석 상태 조회 API
    @GetMapping("/status/{seatId}")
    public ResponseEntity<List<ReservationResponse>> getReservationStatus(@PathVariable Long seatId) {
        List<ReservationResponse> responseList = reserveSeatUseCase.getReservationStatus(seatId);
        return ResponseEntity.ok(responseList);
    }

    // 예약 만료 처리 API
    @PutMapping("/expire/{reservationId}")
    public ResponseEntity<Void> expireReservation(@PathVariable Long reservationId) {
        reserveSeatUseCase.expireReservation(reservationId);
        return ResponseEntity.ok().build();
    }
}
