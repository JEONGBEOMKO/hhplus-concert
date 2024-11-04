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
        String key = parseKey(redisLock.keyExpression(), joinPoint);

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

    private String parseKey(String keyExpression, ProceedingJoinPoint joinPoint) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        // 메서드 파라미터 이름과 값을 SpEL 컨텍스트에 추가
        for (int i = 0; i < signature.getParameterNames().length; i++) {
            context.setVariable(signature.getParameterNames()[i], args[i]);
        }

        // keyExpression을 SpEL로 평가하여 키 생성
        return parser.parseExpression(keyExpression).getValue(context, String.class);
    }
}
