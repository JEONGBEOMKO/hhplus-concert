package com.hhplus.concert.interfaces.api.seat;

import com.hhplus.concert.application.dto.request.ReservationRequest;
import com.hhplus.concert.application.dto.request.SeatRequest;
import com.hhplus.concert.application.dto.response.ReservationResponse;
import com.hhplus.concert.application.dto.response.SeatResponse;
import com.hhplus.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.concert.application.usecase.ReserveSeatUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    private final GetAvailableSeatsUseCase getAvailableSeatsUseCase;
    private final ReserveSeatUseCase reserveSeatUseCase;

    public SeatController(GetAvailableSeatsUseCase getAvailableSeatsUseCase, ReserveSeatUseCase reserveSeatUseCase) {
        this.getAvailableSeatsUseCase = getAvailableSeatsUseCase;
        this.reserveSeatUseCase = reserveSeatUseCase;
    }

    // 예약 가능한 좌석 조회 API
    @GetMapping("/available-seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@RequestBody SeatRequest seatRequest) {
        List<SeatResponse> availableSeats = getAvailableSeatsUseCase.getAvailableSeats(seatRequest.getConcertScheduleId());
        return ResponseEntity.ok(availableSeats);
    }

    // 좌석 예약 요청 API (좌석 임시 배정)
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeat(@RequestBody ReservationRequest request) {
        // 좌석 번호가 1~50 사이인지 검증
        if (request.getSeatId() < 1 || request.getSeatId() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("좌석 번호는 1에서 50 사이여야 합니다.");
        }

        ReservationResponse response = reserveSeatUseCase.reserveSeat(request);
        return ResponseEntity.ok(response);
    }

    // 좌석 임시 예약 해제 및 만료 처리
    @PostMapping("/expire-reservation/{reservationId}")
    public ResponseEntity<Void> expireReservation(@PathVariable Long reservationId) {
        reserveSeatUseCase.expireReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
