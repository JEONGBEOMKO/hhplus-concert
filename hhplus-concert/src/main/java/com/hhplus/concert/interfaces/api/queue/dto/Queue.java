package com.hhplus.concert.interfaces.api.queue.dto;

import java.time.LocalDateTime;

public class Queue {
    private Long userId;
    private String tokenId;
    private Integer queueOrder;
    private Integer timeRemaining;
    private LocalDateTime expirationTime;

    public Queue(Long userId, String tokenId, Integer queueOrder, Integer timeRemaining, LocalDateTime expirationTime) {
        this.userId = userId;
        this.tokenId = tokenId;
        this.queueOrder = queueOrder;
        this.timeRemaining = timeRemaining;
        this.expirationTime = expirationTime;
    }

}
