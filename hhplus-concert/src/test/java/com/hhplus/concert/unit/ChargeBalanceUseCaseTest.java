package com.hhplus.concert.unit;

import com.hhplus.concert.application.dto.response.ChargeResponse;
import com.hhplus.concert.application.usecase.ChargeBalanceUseCase;
import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChargeBalanceUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChargeBalanceUseCase chargeBalanceUseCase;

    private UUID userId;
    private User user;

    @BeforeEach
    public void setUp() {
        userId = UUID.randomUUID();
        user = new User(userId, "Test User", 1000L);
    }

    @Test
    @DisplayName("성공 테스트: 잔액 충전 성공")
    public void testChargeBalance_Success() {
        // Given
        long amountToCharge = 500L;
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ChargeResponse chargeResponse = chargeBalanceUseCase.chargeBalance(userId, amountToCharge);

        // Then
        assertEquals(1500L, chargeResponse.getNewBalance(), "잔액 충전 후 새로운 잔액은 1500이어야 합니다.");
        verify(userRepository, times(1)).save(user); // 저장이 1회 호출되는지 검증
    }

    @Test
    @DisplayName("성공 테스트: 잔액 조회 성공")
    public void testGetBalance_Success() {
        // Given
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        // When
        long balance = chargeBalanceUseCase.getBalance(userId);

        // Then
        assertEquals(1000L, balance, "조회된 잔액은 1000이어야 합니다.");
    }

    @Test
    @DisplayName("실패 테스트: 존재하지 않는 사용자로 인한 잔액 충전 실패")
    public void testChargeBalance_Fail_UserNotFound() {
        // Given
        UUID nonExistentUserId = UUID.randomUUID();
        when(userRepository.findByUserId(nonExistentUserId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            chargeBalanceUseCase.chargeBalance(nonExistentUserId, 500L);
        });
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage(), "존재하지 않는 사용자에 대해 예외가 발생해야 합니다.");
    }

    @Test
    @DisplayName("실패 테스트: 존재하지 않는 사용자로 인한 잔액 조회 실패")
    public void testGetBalance_Fail_UserNotFound() {
        // Given
        UUID nonExistentUserId = UUID.randomUUID();
        when(userRepository.findByUserId(nonExistentUserId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            chargeBalanceUseCase.getBalance(nonExistentUserId);
        });
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage(), "존재하지 않는 사용자에 대해 예외가 발생해야 합니다.");
    }
}
