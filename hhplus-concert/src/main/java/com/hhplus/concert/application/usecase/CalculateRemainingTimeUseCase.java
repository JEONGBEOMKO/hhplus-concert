package com.hhplus.concert.application.usecase;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CalculateRemainingTimeUseCase {
    public long calculateRemainingTime(LocalDateTime expiredAt) {
        return expiredAt.isAfter(LocalDateTime.now()) ?
                Duration.between(LocalDateTime.now(), expiredAt).getSeconds() : 0;
    }
}
