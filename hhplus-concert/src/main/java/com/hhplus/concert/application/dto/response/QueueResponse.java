package com.hhplus.concert.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class QueueResponse {
    private UUID userId;
    private String token;
    private String status;
    private LocalDateTime enteredAt;
    private LocalDateTime expiredAt;
    private int queuePosition;


    public QueueResponse(UUID userId, String token, String status, LocalDateTime enteredAt, LocalDateTime expiredAt, int queuePosition) {
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.enteredAt = enteredAt;
        this.expiredAt = expiredAt;
        this.queuePosition = queuePosition;
    }
}
