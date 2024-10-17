package com.hhplus.concert.application.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class QueueRequest {
    private UUID userId;

    public QueueRequest(UUID userId) {
        this.userId = userId;
    }

}
