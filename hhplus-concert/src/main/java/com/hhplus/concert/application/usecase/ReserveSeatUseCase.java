package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.input.ReservationInput;
import com.hhplus.concert.application.dto.output.ReservationOutput;
import com.hhplus.concert.config.aop.annotation.RedisLock;
import com.hhplus.concert.domain.reservation.Reservation;
import com.hhplus.concert.domain.seat.Seat;
import com.hhplus.concert.infrastructure.repository.ReservationRepository;
import com.hhplus.concert.infrastructure.repository.SeatRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ReserveSeatUseCase {
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public ReserveSeatUseCase(ReservationRepository reservationRepository, SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
    }

    // 좌석 임시 예약 처리 메서드
    @RedisLock(keyExpression = "'seat-lock:' + #reservationInput.seatId", waitTime = 5, leaseTime = 10)
    public ReservationOutput reserveSeat(ReservationInput reservationInput) {
        Seat seat = seatRepository.findById(reservationInput.getSeatId())
                .orElseThrow(() -> new NoSuchElementException("좌석을 찾을 수 없습니다."));

        if (!seat.getSeatStatus().equals("AVAILABLE")) {
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }

        seat.setSeatStatus("TEMPORARY");
        seatRepository.save(seat);

        Reservation reservation = new Reservation(
                reservationInput.getUserId(),
                seat.getId(),
                reservationInput.getSeatAmount(),
                reservationInput.getSeatPosition(),
                "TEMPORARY"
        );

        Reservation savedReservation = reservationRepository.save(reservation);

        return new ReservationOutput(
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
    public List<ReservationOutput> getReservationStatus(Long seatId) {
        List<Reservation> reservations = reservationRepository.findAllBySeatId(seatId);
        return reservations.stream()
                .map(reservation -> new ReservationOutput(
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
                .orElseThrow(() -> new NoSuchElementException("예약을 찾을 수 없습니다."));

        reservation.setStatus("EXPIRED");
        reservationRepository.save(reservation);

        Seat seat = seatRepository.findById(reservation.getSeatId())
                .orElseThrow(() -> new NoSuchElementException("좌석을 찾을 수 없습니다."));
        seat.setSeatStatus("AVAILABLE");
        seatRepository.save(seat);
    }
}

