package com.hhplus.concert.infrastructure.repository;

import com.hhplus.concert.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByConcertScheduleId(Long concertScheduleId);
}
