package com.hhplus.concert.infrastructure.event.payment;

import com.hhplus.concert.domain.payment.event.PaymentCompletedEvent;
import com.hhplus.concert.infrastructure.event.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher implements EventPublisher<PaymentCompletedEvent> {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PaymentEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(PaymentCompletedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
