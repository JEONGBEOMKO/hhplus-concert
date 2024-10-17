package com.hhplus.concert.infrastructure.repository;

import com.hhplus.concert.domain.concertschedule.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Long> {
    // 특정 날짜에 열리는 콘서트 스케줄을 조회하는 메서드
    List<ConcertSchedule> findAllByOpenAt(LocalDate openAt);
}
