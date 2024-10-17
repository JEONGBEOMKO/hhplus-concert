package com.hhplus.concert.application.usecase;

import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class QueueProcessingScheduler {
    private final QueueRepository queueRepository;
    private static final int MAX_ACTIVE_COUNT = 5;

    public QueueProcessingScheduler(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    // 1분마다 대기열에서 만료된 항목 처리
    @Scheduled(fixedRate = 60000)  // 1분마다 실행
    public void processExpiredQueues() {
        LocalDateTime now = LocalDateTime.now();
        List<Queue> expiredQueues = queueRepository.findAllByExpiredAtBeforeAndStatus(now, "WAITING");

        for (Queue queue : expiredQueues) {
            queue.setStatus("EXPIRED");
            queueRepository.save(queue);
        }
    }

    // 대기열 순차 활성화
    @Scheduled(fixedRate = 60000)
    public void activateNextInQueue(UUID userId) {
        long activeCount = queueRepository.countByStatus("ACTIVE");

        // 활성화 가능한 항목이 더 있는지 확인
        if (activeCount < MAX_ACTIVE_COUNT) {
            List<Queue> waitingQueues = queueRepository.findAllByUserIdAndStatus(userId,"WAITING");

            if (!waitingQueues.isEmpty()) {
                // 활성화할 대기열 항목 수를 최대 활성화 가능 수로 제한
                int remainingActivations = MAX_ACTIVE_COUNT - (int) activeCount;
                List<Queue> nextInQueue = waitingQueues.subList(0, Math.min(remainingActivations, waitingQueues.size()));

                for (Queue queue : nextInQueue) {
                    queue.setStatus("ACTIVE");
                    queueRepository.save(queue);
                }
            }
        }
    }


}
