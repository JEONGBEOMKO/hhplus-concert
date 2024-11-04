package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.output.ChargeOutput;
import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DeductBalanceUseCase {
    private final UserRepository userRepository;

    public DeductBalanceUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 포인트 차감 로직
    @Transactional
    public ChargeOutput deductBalance(UUID userId, Long amount) {
        User user = userRepository.findByUserIdWithPessimisticLock(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        user.deductAmount(amount);
        userRepository.save(user);

        return new ChargeOutput(user.getUserId(), -amount, user.getAmount());
    }
}
