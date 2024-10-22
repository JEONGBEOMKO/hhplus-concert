package com.hhplus.concert.interfaces.api.queue.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueResponse {
    private UUID userId;
    private String token;
    private String status;
    private LocalDateTime enteredAt;
    private LocalDateTime expiredAt;
    private int queuePosition;
    private long currentPosition;  // 현재 사용자의 대기열 순서
    private long activeCount;      // 활성화된 대기열 수
    private long remainingTime;    // 남은 시간
}
