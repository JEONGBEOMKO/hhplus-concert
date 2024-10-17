package com.hhplus.concert.application.usecase;


import com.hhplus.concert.application.dto.request.QueueRequest;
import com.hhplus.concert.application.dto.response.QueueResponse;
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
    public QueueResponse generateToken(QueueRequest queueRequest) {
        UUID userId = queueRequest.getUserId();  // 유저 ID를 요청에서 가져옴
        String token = UUID.randomUUID().toString();  // 유저 토큰 생성
        String status = "WAITING";  // 초기 대기 상태 설정
        LocalDateTime enteredAt = LocalDateTime.now();  // 현재 시간
        LocalDateTime expiredAt = enteredAt.plusMinutes(5);  // 만료 시간 (5분 후)

        // 대기 순서를 현재 대기 중인 사용자 수에 +1로 설정
        int queuePosition = (int) queueRepository.countByStatus("WAITING") + 1;

        // Queue 엔티티 생성
        Queue newQueue = new Queue(userId, token, status, enteredAt, expiredAt, queuePosition);

        // 저장 및 응답 반환
        Queue savedQueue = queueRepository.save(newQueue);


        return new QueueResponse(
                savedQueue.getUserId(),
                savedQueue.getToken(),
                savedQueue.getStatus(),
                savedQueue.getEnteredAt(),
                savedQueue.getExpiredAt(),
                savedQueue.getQueuePosition()
        );
    }

    // 토큰을 통해 유저의 대기열 정보를 가져오는 메서드
    public QueueResponse getQueuePosition(String token) {
        Queue queue = queueRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        return new QueueResponse(
                queue.getUserId(),
                queue.getToken(),
                queue.getStatus(),
                queue.getEnteredAt(),
                queue.getExpiredAt(),
                queue.getQueuePosition()
        );
    }
}
