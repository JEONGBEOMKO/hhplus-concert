package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.output.ChargeOutput;
import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ChargeBalanceUseCase {
    private final UserRepository userRepository;

    public ChargeBalanceUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 잔액 충전 로직
    public ChargeOutput chargeBalance(UUID userId, Long amount){
        User user =userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        long newBalance = user.getAmount() + amount;
        user.setAmount(newBalance);
        userRepository.save(user);

        return new ChargeOutput(user.getUserId(), amount, newBalance);
    }

}
