package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.request.SeatRequest;
import com.hhplus.concert.application.dto.response.SeatResponse;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAvailableSeatsUseCase {

    private  final SeatRepository seatRepository;

    public GetAvailableSeatsUseCase(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // 예약 가능한 좌석 목록을 조회
    public List<SeatResponse> getAvailableSeats(Long concertScheduleId) {
        List<Seat> availableSeats = seatRepository.findByConcertScheduleId(concertScheduleId);

        return availableSeats.stream()
                .map(seat -> new SeatResponse(
                        seat.getId(),
                        seat.getConcertScheduleId(),
                        seat.getAmount(),
                        seat.getPosition(),
                        seat.getSeatStatus()
                ))
                .collect(Collectors.toList());
    }
}
