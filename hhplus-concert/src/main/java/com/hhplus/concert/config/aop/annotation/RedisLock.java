package com.hhplus.concert.config.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {
    int keyParameterIndex();
    String keyPrefix() default "seat-lock:";
    long waitTime() default 5;  // 락을 기다리는 최대 시간 (초)
    long leaseTime() default 10; // 락 점유 시간 (초)
}
