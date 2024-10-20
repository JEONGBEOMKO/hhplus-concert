package com.hhplus.concert.interfaces.api.queue;

import com.hhplus.concert.application.dto.input.QueueInput;
import com.hhplus.concert.application.dto.output.QueueOutput;
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
    public ResponseEntity<QueueOutput> generateQueueToken(@RequestBody QueueInput queueInput) {
        QueueOutput response = generateQueueTokenUseCase.generateToken(queueInput);
        return ResponseEntity.ok(response);
    }

    // 대기열 위치 조회 API
    @GetMapping("/position/{token}")
    public ResponseEntity<QueueOutput> getQueuePosition(@PathVariable String token) {
        QueueOutput response = generateQueueTokenUseCase.getQueuePosition(token);
        return ResponseEntity.ok(response);
    }
}
