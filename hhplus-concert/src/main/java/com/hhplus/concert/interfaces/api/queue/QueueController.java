package com.hhplus.concert.interfaces.api.queue;

import com.hhplus.concert.interfaces.api.queue.dto.Queue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/queue")
public class QueueController {

    // 유저 대기열 토큰 발급
    @PostMapping("/users/{userId}/token")
    public ResponseEntity<Queue> generateQueueToken(@PathVariable Long userId){
        Queue queueToken = new Queue(userId, "dummyTokenId", 1, 100, LocalDateTime.now().plusMinutes(5));
        return ResponseEntity.ok(queueToken);
    }

    // 유저 대기열 상태 조회 API
    @GetMapping("/users/{userId}/token")
    public ResponseEntity<Queue> getQueueStatus(@PathVariable Long userId) {
        Queue queueToken = new Queue(userId, "dummyTokenId", 1, 60, LocalDateTime.now().plusMinutes(3));
        return ResponseEntity.ok(queueToken);
    }
}
