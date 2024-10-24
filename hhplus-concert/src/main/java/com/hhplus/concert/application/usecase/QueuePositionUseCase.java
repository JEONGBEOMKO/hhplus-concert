package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.output.QueueOutput;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class QueuePositionUseCase {
    private final QueueRepository queueRepository;
    private final CalculateRemainingTimeUseCase calculateRemainingTimeUseCase;

    public QueuePositionUseCase(QueueRepository queueRepository, CalculateRemainingTimeUseCase calculateRemainingTimeUseCase) {
        this.queueRepository = queueRepository;
        this.calculateRemainingTimeUseCase = calculateRemainingTimeUseCase;
    }

    // 대기열에서 현재 몇 번째 순서인지 조회하는 메서드
    public QueueOutput getQueuePosition(String token, UUID userId) {
        Queue queue = queueRepository.findByTokenAndUserId(token, userId)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 토큰입니다."));

        // 현재 활성화된 대기열 수 확인
        long activeCount = queueRepository.countByStatus("ACTIVE");

        // 현재 사용자보다 앞에 있는 대기열 수 확인
        long currentPosition = queueRepository.countByStatusAndQueuePositionLessThan("WAITING", queue.getQueuePosition()) + 1;

        long remainingTime = calculateRemainingTimeUseCase.calculateRemainingTime(queue.getExpiredAt());

        return new QueueOutput(
                queue.getUserId(),
                queue.getToken(),
                queue.getStatus(),
                queue.getEnteredAt(),
                queue.getExpiredAt(),
                queue.getQueuePosition(),
                currentPosition,
                activeCount,
                remainingTime
        );
    }
}
