package com.hhplus.concert.infrastructure.repository;

import com.hhplus.concert.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByUserIdAndSeatIdAndStatus(UUID userId, Long seatId, String status);

    List<Reservation> findBySeatIdAndStatus(Long seatId, String status);

    List<Reservation> findByReservedExpiresAtBefore(LocalDateTime now);

    List<Reservation> findByUserIdAndStatus(UUID userId, String status);

    List<Reservation> findAllBySeatId(Long seatId);
}
