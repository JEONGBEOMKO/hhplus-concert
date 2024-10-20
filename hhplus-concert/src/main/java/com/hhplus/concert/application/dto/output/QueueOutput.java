package com.hhplus.concert.application.dto.output;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueOutput {
    private UUID userId;
    private String token;
    private String status;
    private LocalDateTime enteredAt;
    private LocalDateTime expiredAt;
    private int queuePosition;
}
