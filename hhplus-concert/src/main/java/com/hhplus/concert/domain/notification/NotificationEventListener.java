package com.hhplus.concert.domain.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotification(NotificationEvent event) {
        // 실제 알림 전송 로직 예시 (외부 API 호출 등)
        try {
            // 예시 코드: 실제 알림 서비스 호출
            // notificationService.send(event.getMessage());
            log.info("알림톡 전송 완료했습니다. : {}", event.getMessage());
        } catch (Exception e) {
            log.error("알림톡 전송에 실패했습니다. : {}", event.getMessage(), e);
        }
    }
}
