package com.hhplus.concert.application.usecase;

import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class QueueProcessingScheduler {
    private final RedissonClient redissonClient;
    private static final int MAX_ACTIVE_COUNT = 5;
    private static final String QUEUE_KEY = "userQueue";

    public QueueProcessingScheduler(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    // 1분마다 대기열에서 만료된 항목 처리
    @Scheduled(fixedRate = 60000)  // 1분마다 실행
    public void processExpiredQueues() {
        RScoredSortedSet<String> queueSet = redissonClient.getScoredSortedSet(QUEUE_KEY);
        double currentTime = Instant.now().getEpochSecond();

        // 만료된 항목 제거
        int removedCount = queueSet.removeRangeByScore(0, true, currentTime, true);
        System.out.println("Removed expired entries: " + removedCount);
    }

    // 대기열 순차 활성화
    @Scheduled(fixedRate = 60000)
    public void activateNextInQueue() {
        RScoredSortedSet<String> queueSet = redissonClient.getScoredSortedSet(QUEUE_KEY);
        long activeCount = queueSet.size();

        if (activeCount < MAX_ACTIVE_COUNT) {
            queueSet.stream()
                    .limit(MAX_ACTIVE_COUNT - activeCount)
                    .forEach(token -> {
                        System.out.println("Activating user with token: " + token);
                    });
        }
    }
}
