package com.hhplus.concert.interfaces.api.queue;

import com.hhplus.concert.application.dto.input.QueueInput;
import com.hhplus.concert.application.dto.output.QueueOutput;
import com.hhplus.concert.application.usecase.GenerateQueueTokenUseCase;

import com.hhplus.concert.application.usecase.QueuePositionUseCase;
import com.hhplus.concert.interfaces.api.queue.dto.QueueRequest;
import com.hhplus.concert.interfaces.api.queue.dto.QueueResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/queues")
public class QueueController {
    private final GenerateQueueTokenUseCase generateQueueTokenUseCase;
    private final QueuePositionUseCase queuePositionUseCase;

    public QueueController(GenerateQueueTokenUseCase generateQueueTokenUseCase, QueuePositionUseCase queuePositionUseCase) {
        this.generateQueueTokenUseCase = generateQueueTokenUseCase;
        this.queuePositionUseCase = queuePositionUseCase;
    }

    // 유저 대기열 토큰 발급 API
    @PostMapping("/generate-token")
    public ResponseEntity<QueueResponse> generateQueueToken(@RequestBody QueueRequest queueRequest) {
        QueueInput queueInput = new QueueInput(queueRequest.getUserId());
        QueueOutput queueOutput = generateQueueTokenUseCase.generateToken(queueInput);

        QueueResponse response = new QueueResponse(
                queueOutput.getUserId(),
                queueOutput.getToken(),
                queueOutput.getStatus(),
                queueOutput.getEnteredAt(),
                queueOutput.getExpiredAt(),
                queueOutput.getQueuePosition(),
                queueOutput.getCurrentPosition(),
                queueOutput.getActiveCount(),
                queueOutput.getRemainingTime()
        );

        return ResponseEntity.ok(response);
    }

    // 대기열 위치 조회 API
    @GetMapping("/position/{token}")
    public ResponseEntity<QueueResponse> getQueuePosition(@PathVariable String token , @RequestHeader("User-Id") String userIdHeader) {
        UUID userId = UUID.fromString(userIdHeader);

        QueueOutput queueOutput = queuePositionUseCase.getQueuePosition(token, userId);

        QueueResponse response = new QueueResponse(
                queueOutput.getUserId(),
                queueOutput.getToken(),
                queueOutput.getStatus(),
                queueOutput.getEnteredAt(),
                queueOutput.getExpiredAt(),
                queueOutput.getQueuePosition(),
                queueOutput.getCurrentPosition(),
                queueOutput.getActiveCount(),
                queueOutput.getRemainingTime()
        );
        return ResponseEntity.ok(response);
    }
}
