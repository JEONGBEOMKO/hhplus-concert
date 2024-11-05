package com.hhplus.concert.config.aop;

import com.hhplus.concert.config.aop.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
//@Order(1) AopForTransaction 클래스 사용 안 하고 메서드에 @Transactional 처리
@Component
public class RedisLockAspect {
    private final RedissonClient redissonClient;
    private  final AopForTransaction aopForTransaction;
    private final ExpressionParser parser = new SpelExpressionParser();

    public RedisLockAspect(RedissonClient redissonClient, AopForTransaction aopForTransaction) {
        this.redissonClient = redissonClient;
        this.aopForTransaction = aopForTransaction;
    }

    @Around("@annotation(redisLock)")
    public Object aroundRedisLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable{
        String key = generateKey(joinPoint, redisLock);

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

    // 고유 키 생성 로직을 keyGenerator를 통해 사용
    private String generateKey(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Exception {
        if (!redisLock.keyGenerator().isEmpty()) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Method keyGenMethod = joinPoint.getTarget().getClass().getDeclaredMethod(redisLock.keyGenerator(), method.getParameterTypes());
            keyGenMethod.setAccessible(true);
            return (String) keyGenMethod.invoke(joinPoint.getTarget(), joinPoint.getArgs());
        } else {
            throw new IllegalArgumentException("keyGenerator는 지정되어야 합니다.");
        }
    }
}
