package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.request.ReservationRequest;
import com.hhplus.concert.application.dto.response.ReservationResponse;
import com.hhplus.concert.domain.reservation.Reservation;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.ReservationRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReserveSeatUseCase {
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public ReserveSeatUseCase(ReservationRepository reservationRepository, SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
    }

    // 좌석 예약 처리 메서드
    public ReservationResponse reserveSeat(ReservationRequest request) {
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));

        if (!seat.getSeatStatus().equals("AVAILABLE")) {
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }

        // 좌석을 임시 예약 상태로 변경
        seat.setSeatStatus("TEMPORARY");
        seatRepository.save(seat);

        // 예약 정보를 저장
        Reservation reservation = new Reservation(
                request.getUserId(),
                seat.getId(),
                request.getSeatAmount(),
                seat.getPosition(),
                "TEMPORARY"
        );

        Reservation savedReservation = reservationRepository.save(reservation);

        return new ReservationResponse(
                savedReservation.getId(),
                savedReservation.getUserId(),
                savedReservation.getSeatId(),
                savedReservation.getSeatAmount(),
                savedReservation.getSeatPosition(),
                savedReservation.getStatus(),
                savedReservation.getReservedAt(),
                savedReservation.getReservedExpiresAt()
        );
    }

    // 예약된 좌석의 상태 조회 메서드
    public List<ReservationResponse> getReservationStatus(Long seatId) {
        List<Reservation> reservations = reservationRepository.findAllBySeatId(seatId);
        return reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getUserId(),
                        reservation.getSeatId(),
                        reservation.getSeatAmount(),
                        reservation.getSeatPosition(),
                        reservation.getStatus(),
                        reservation.getReservedAt(),
                        reservation.getReservedExpiresAt()
                ))
                .collect(Collectors.toList());
    }

    // 예약 만료 처리 메서드
    public void expireReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));

        // 만료 처리
        reservation.setStatus("EXPIRED");
        reservationRepository.save(reservation);

        // 좌석 상태 업데이트
        Seat seat = seatRepository.findById(reservation.getSeatId())
                .orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
        seat.setSeatStatus("AVAILABLE");
        seatRepository.save(seat);
    }
}

