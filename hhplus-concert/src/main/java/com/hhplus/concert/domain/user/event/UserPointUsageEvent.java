package com.hhplus.concert.domain.user.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPointUsageEvent {
    private UUID userId;
    private long amount;
}
