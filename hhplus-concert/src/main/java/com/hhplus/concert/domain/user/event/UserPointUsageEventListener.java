package com.hhplus.concert.domain.user.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPointUsageEventListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserPointUsage(UserPointUsageEvent event){

        log.info("포인트 사용이력 저장 userId: {} amount: {}", event.getUserId(), event.getAmount());
        // 포인트 사용이력 저장 로직
        // userService.saveUserPointUsage(event.getUserId(), event.getAmount());
    }
}
