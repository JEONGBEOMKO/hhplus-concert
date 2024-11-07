package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.output.QueueOutput;
import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class QueuePositionUseCase {
    private final RedissonClient redissonClient;
    private static final String QUEUE_KEY = "userQueue";

    public QueuePositionUseCase(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    // 대기열에서 현재 몇 번째 순서인지 조회하는 메서드
    public long getQueuePosition(String token) {
        RScoredSortedSet<String> queueSet = redissonClient.getScoredSortedSet(QUEUE_KEY);
        Integer rank = queueSet.rank(token);

        if (rank == null) {
            throw new NoSuchElementException("유효하지 않은 토큰입니다.");
        }

        // Redis의 rank는 0부터 시작하므로 1을 더해서 순서 반환
        return rank + 1;
    }
}
