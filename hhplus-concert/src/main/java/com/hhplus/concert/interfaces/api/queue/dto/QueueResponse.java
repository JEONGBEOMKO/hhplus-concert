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
}
