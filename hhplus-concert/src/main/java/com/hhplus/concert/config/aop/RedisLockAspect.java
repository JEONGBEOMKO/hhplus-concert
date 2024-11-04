package com.hhplus.concert.config.aop;

import com.hhplus.concert.config.aop.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisLockAspect {
    private final RedissonClient redissonClient;
    private  final AopForTransaction aopForTransaction;

    public RedisLockAspect(RedissonClient redissonClient, AopForTransaction aopForTransaction) {
        this.redissonClient = redissonClient;
        this.aopForTransaction = aopForTransaction;
    }

    @Around("@annotation(redisLock)")
    public Object aroundRedisLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable{
        Object keyParam = joinPoint.getArgs()[redisLock.keyParameterIndex()];
        String key = redisLock.keyPrefix() + keyParam.toString();

        RLock lock = redissonClient.getLock(key);

        try{
            if (lock.tryLock(redisLock.waitTime(), redisLock.leaseTime(), TimeUnit.SECONDS)) {
                return aopForTransaction.proceed(joinPoint);
            } else {
                throw new IllegalStateException("락을 획득할 수 없습니다. 다시 시도해주세요.");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("락을 획득하는 중 오류가 발생했습니다.", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

    }
}
