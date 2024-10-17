package com.hhplus.concert.infrastructure.repository;

import com.hhplus.concert.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // 좌석 상태 조회 및 업데이트
    Optional<Reservation> findByUserIdAndSeatIdAndStatus(UUID userId, Long seatId, String status);

    Optional<Reservation> findBySeatIdAndStatus(Long seatId, String status);

    // 예약 만료 상태 처리
    List<Reservation> findByReservedExpiresAtBefore(LocalDateTime currentTime);

    // 유저의 특정 상태의 예약 조회
    Optional<Reservation> findByUserIdAndStatus(UUID userId, String status);

    // 예약된 좌석의 상태 조회
    List<Reservation> findAllBySeatId(Long seatId);
}
