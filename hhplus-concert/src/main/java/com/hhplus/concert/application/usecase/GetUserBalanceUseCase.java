package com.hhplus.concert.application.usecase;

import com.hhplus.concert.application.dto.output.UserOutput;
import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GetUserBalanceUseCase {
    private final UserRepository userRepository;

    public GetUserBalanceUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 잔액 조회 메서드
    public UserOutput getUserById(UUID userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        return new UserOutput(user.getUserId(), user.getName(), user.getAmount());
    }
}
