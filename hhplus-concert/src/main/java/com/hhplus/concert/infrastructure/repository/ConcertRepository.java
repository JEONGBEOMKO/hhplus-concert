package com.hhplus.concert.infrastructure.repository;

import com.hhplus.concert.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Long>{
    // 특정 제목으로 콘서트를 검색
    Optional<Concert> findByTitle(String title);

    // 특정 날짜 이후에 열리는 콘서트 목록 조회
    List<Concert> findAllByCreatedAtAfter(LocalDate date);

    // 콘서트 제목에 특정 키워드가 포함된 콘서트 목록 조회
    List<Concert> findByTitleContaining(String keyword);

    // 콘서트 ID로 존재 여부 확인
    boolean existsById(Long concertId);
}
