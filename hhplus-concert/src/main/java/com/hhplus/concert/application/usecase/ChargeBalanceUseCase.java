package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.response.ChargeResponse;
import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChargeBalanceUseCase {
    private final UserRepository userRepository;

    public ChargeBalanceUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ChargeResponse chargeBalance(UUID userId, Long amount){
        User user =userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setAmount(user.getAmount() + amount);
        userRepository.save(user);

        return new ChargeResponse(userId, user.getAmount());
    }
}
