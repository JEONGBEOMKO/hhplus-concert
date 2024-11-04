package com.hhplus.concert.integration;

import com.hhplus.concert.application.usecase.DeductBalanceUseCase;
import com.hhplus.concert.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/test_delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class DeductBalanceConcurrencyTest {
    @Autowired
    private DeductBalanceUseCase deductBalanceUseCase;

    @Autowired
    private UserRepository userRepository;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    }

    @Test
    void givenConcurrentDeductRequests_whenExecute_thenBalanceDecreasesCorrectly() throws InterruptedException {
        // given
        long initialBalance = userRepository.findByUserId(userId).orElseThrow().getAmount();
        long amountToDeduct = 500L;
        int numberOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    deductBalanceUseCase.deductBalance(userId, amountToDeduct);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();

        // then
        long finalBalance = userRepository.findByUserId(userId).orElseThrow().getAmount();
        long expectedBalance = initialBalance - (amountToDeduct * numberOfThreads);

        assertEquals(expectedBalance, finalBalance, "동시 차감 후 최종 잔액이 예상과 다릅니다.");
    }
}
