package com.hhplus.concert.application.usecase;

import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserBalanceUseCase {
    private final UserRepository userRepository;

    public GetUserBalanceUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 잔액 조회 메서드
    public Long getBalance(UUID userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return user.getAmount();
    }
}
