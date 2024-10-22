package com.hhplus.concert.interfaces.api.reservation;

import com.hhplus.concert.application.dto.input.ConcertScheduleInput;
import com.hhplus.concert.application.dto.input.ReservationInput;
import com.hhplus.concert.application.dto.output.ConcertScheduleOutput;
import com.hhplus.concert.application.dto.output.ReservationOutput;
import com.hhplus.concert.application.dto.output.SeatOutput;
import com.hhplus.concert.application.usecase.GetAvailableDatesUseCase;
import com.hhplus.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.concert.application.usecase.ReserveSeatUseCase;
import com.hhplus.concert.interfaces.api.reservation.dto.ReservationRequest;
import com.hhplus.concert.interfaces.api.reservation.dto.ReservationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    // 좌석 예약 API
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeat(@RequestBody ReservationRequest reservationRequest) {
        // 좌석 번호가 1~50 사이인지 검증
        if (reservationRequest.getSeatId() < 1 || reservationRequest.getSeatId() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("좌석 번호는 1에서 50 사이여야 합니다.");  // BAD_REQUEST 상태 응답 메시지 포함
        }

        // Request -> Input 변환
        ReservationInput reservationInput = new ReservationInput(
                reservationRequest.getUserId(),
                reservationRequest.getSeatId(),
                reservationRequest.getSeatAmount(),
                reservationRequest.getSeatPosition()
        );

        ReservationOutput reservationOutput = reserveSeatUseCase.reserveSeat(reservationInput);

        // Output -> Response 변환
        ReservationResponse reservationResponse = new ReservationResponse(
                reservationOutput.getReservationId(),
                reservationOutput.getUserId(),
                reservationOutput.getSeatId(),
                reservationOutput.getSeatAmount(),
                reservationOutput.getSeatPosition(),
                reservationOutput.getStatus(),
                reservationOutput.getReservedAt(),
                reservationOutput.getReservedExpiresAt()
        );

        return ResponseEntity.ok(reservationResponse);
    }

    // 좌석 임시 예약 해제 및 만료 처리
    @PostMapping("/expire-reservation/{reservationId}")
    public ResponseEntity<Void> expireReservation(@PathVariable Long reservationId) {
        reserveSeatUseCase.expireReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
