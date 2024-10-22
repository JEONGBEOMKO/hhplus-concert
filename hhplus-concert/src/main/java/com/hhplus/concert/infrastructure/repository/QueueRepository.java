package com.hhplus.concert.infrastructure.repository;

import com.hhplus.concert.domain.queue.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueueRepository extends JpaRepository<Queue, Long> {
    Optional<Queue> findByUserIdAndStatus(UUID userId, String status);
    Optional<Queue> findByToken(String token);
    long countByStatus(String status);
    long countByStatusAndQueuePositionLessThan(String status, int queuePosition);

    List<Queue> findAllByExpiredAtBeforeAndStatus(LocalDateTime expiredAt, String status);
    List<Queue> findAllByUserIdAndStatus(UUID userId, String status);
}
