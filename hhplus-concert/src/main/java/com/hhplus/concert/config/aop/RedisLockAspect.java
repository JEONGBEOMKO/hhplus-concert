package com.hhplus.concert.config.aop;

import com.hhplus.concert.config.aop.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.concurrent.TimeUnit;

public class RedisLockAspect {
    private final RedissonClient redissonClient;
    private  final ExpressionParser parser = new SpelExpressionParser();

    public RedisLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(redisLock)")
    public Object aroundRedisLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable{
        StandardEvaluationContext context = new StandardEvaluationContext();
        String keyExpression = redisLock.key();
        String key = parser.parseExpression(keyExpression).getValue(context, String.class);

        RLock lock = redissonClient.getLock(key);

        try{
            if (lock.tryLock(redisLock.waitTime(), redisLock.leaseTime(), TimeUnit.SECONDS)) {
                return joinPoint.proceed();
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
