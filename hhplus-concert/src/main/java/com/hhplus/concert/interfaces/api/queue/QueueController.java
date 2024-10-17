package com.hhplus.concert.interfaces.api.queue;

import com.hhplus.concert.application.dto.request.QueueRequest;
import com.hhplus.concert.application.dto.response.QueueResponse;
import com.hhplus.concert.application.usecase.GenerateQueueTokenUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queues")
public class QueueController {
    private final GenerateQueueTokenUseCase generateQueueTokenUseCase;

    public QueueController(GenerateQueueTokenUseCase generateQueueTokenUseCase) {
        this.generateQueueTokenUseCase = generateQueueTokenUseCase;
    }

    // 유저 대기열 토큰 발급 API
    @PostMapping("/generate-token")
    public ResponseEntity<QueueResponse> generateQueueToken(@RequestBody QueueRequest queueRequest) {
        QueueResponse response = generateQueueTokenUseCase.generateToken(queueRequest);
        return ResponseEntity.ok(response);
    }

    // 대기열 위치 조회 API
    @GetMapping("/position/{token}")
    public ResponseEntity<QueueResponse> getQueuePosition(@PathVariable String token) {
        QueueResponse response = generateQueueTokenUseCase.getQueuePosition(token);
        return ResponseEntity.ok(response);
    }
}
