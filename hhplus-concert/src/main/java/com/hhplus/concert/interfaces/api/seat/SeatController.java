package com.hhplus.concert.interfaces.api.seat;

import com.hhplus.concert.application.dto.input.ReservationInput;
import com.hhplus.concert.application.dto.input.SeatInput;
import com.hhplus.concert.application.dto.output.ReservationOutput;
import com.hhplus.concert.application.dto.output.SeatOutput;
import com.hhplus.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.concert.application.usecase.ReserveSeatUseCase;
import com.hhplus.concert.interfaces.api.reservation.dto.ReservationRequest;
import com.hhplus.concert.interfaces.api.seat.dto.SeatRequest;
import com.hhplus.concert.interfaces.api.seat.dto.SeatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        SeatInput seatInput = new SeatInput(seatRequest.getConcertScheduleId());
        List<SeatOutput> availableSeats = getAvailableSeatsUseCase.getAvailableSeats(seatInput.getConcertScheduleId());
        List<SeatResponse> seatResponses = availableSeats.stream()
                .map(seatOutput -> new SeatResponse(
                        seatOutput.getId(),
                        seatOutput.getConcertScheduleId(),
                        seatOutput.getAmount(),
                        seatOutput.getPosition(),
                        seatOutput.getSeatStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(seatResponses);
    }

    // 좌석 상태 확인 API
    @GetMapping("/seat-status/{seatId}")
    public ResponseEntity<SeatResponse> getSeatStatus(@PathVariable Long seatId) {
        SeatOutput seatOutput = getAvailableSeatsUseCase.getSeatStatus(seatId);
        SeatResponse seatResponse = new SeatResponse(
                seatOutput.getId(),
                seatOutput.getConcertScheduleId(),
                seatOutput.getAmount(),
                seatOutput.getPosition(),
                seatOutput.getSeatStatus()
        );
        return ResponseEntity.ok(seatResponse);
    }
}
