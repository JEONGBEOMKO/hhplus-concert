package com.hhplus.concert.infrastructure.event;

public interface EventPublisher<T> {
    void publish(T event);
}
