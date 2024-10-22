package com.hhplus.concert.application.usecase;


import com.hhplus.concert.application.dto.input.QueueInput;
import com.hhplus.concert.application.dto.output.QueueOutput;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.infrastructure.repository.QueueRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.UUID;

@Service
public class GenerateQueueTokenUseCase {

    private final QueueRepository queueRepository;
    private final CalculateRemainingTimeUseCase calculateRemainingTimeUseCase;

    public GenerateQueueTokenUseCase(QueueRepository queueRepository, CalculateRemainingTimeUseCase calculateRemainingTimeUseCase) {
        this.queueRepository = queueRepository;
        this.calculateRemainingTimeUseCase = calculateRemainingTimeUseCase;
    }

    // 유저 대기열 토큰을 생성하는 메서드
    public QueueOutput generateToken(QueueInput queueInput) {
        UUID userId = queueInput.getUserId();
        String token = UUID.randomUUID().toString();
        String status = "WAITING";
        LocalDateTime enteredAt = LocalDateTime.now();
        LocalDateTime expiredAt = enteredAt.plusMinutes(5);

        // 대기 순서를 현재 대기 중인 사용자 수에 +1로 설정
        int queuePosition = (int) queueRepository.countByStatus("WAITING") + 1;
        Queue newQueue = new Queue(userId, token, status, enteredAt, expiredAt, queuePosition);

        Queue savedQueue = queueRepository.save(newQueue);


        return new QueueOutput(
                savedQueue.getUserId(),
                savedQueue.getToken(),
                savedQueue.getStatus(),
                savedQueue.getEnteredAt(),
                savedQueue.getExpiredAt(),
                savedQueue.getQueuePosition(),
                calculateRemainingTimeUseCase.calculateRemainingTime(savedQueue.getExpiredAt()), // 남은 시간 계산은 별도 유스케이스로 처리
                queueRepository.countByStatus("ACTIVE"),
                queueRepository.countByStatusAndQueuePositionLessThan("WAITING", savedQueue.getQueuePosition()) + 1
        );
    }

}
