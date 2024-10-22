package com.hhplus.concert.interfaces.api.queue.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueRequest {
    private UUID userId;
}
