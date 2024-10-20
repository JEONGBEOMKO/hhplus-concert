package com.hhplus.concert.application.usecase;


import com.hhplus.concert.application.dto.input.QueueInput;
import com.hhplus.concert.application.dto.output.QueueOutput;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.infrastructure.repository.QueueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.UUID;

@Service
public class GenerateQueueTokenUseCase {
    @Autowired
    private final QueueRepository queueRepository;

    public GenerateQueueTokenUseCase(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
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
                savedQueue.getQueuePosition()
        );
    }

    // 토큰을 통해 유저의 대기열 정보를 가져오는 메서드
    public QueueOutput getQueuePosition(String token) {
        Queue queue = queueRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        return new QueueOutput(
                queue.getUserId(),
                queue.getToken(),
                queue.getStatus(),
                queue.getEnteredAt(),
                queue.getExpiredAt(),
                queue.getQueuePosition()
        );
    }
}
