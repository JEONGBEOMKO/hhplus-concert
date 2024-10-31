package com.hhplus.concert.interfaces.api.user;

import com.hhplus.concert.application.dto.input.ChargeInput;
import com.hhplus.concert.application.dto.output.ChargeOutput;
import com.hhplus.concert.application.dto.output.UserOutput;
import com.hhplus.concert.application.usecase.ChargeBalanceUseCase;
import com.hhplus.concert.application.usecase.DeductBalanceUseCase;
import com.hhplus.concert.application.usecase.GetUserBalanceUseCase;
import com.hhplus.concert.domain.user.User;
import com.hhplus.concert.interfaces.api.user.dto.ChargeRequest;
import com.hhplus.concert.interfaces.api.user.dto.ChargeResponse;
import com.hhplus.concert.interfaces.api.user.dto.UserRequest;
import com.hhplus.concert.interfaces.api.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ChargeBalanceUseCase chargeBalanceUseCase;
    private final GetUserBalanceUseCase getUserBalanceUseCase;
    private final DeductBalanceUseCase deductBalanceUseCase;

    public UserController(ChargeBalanceUseCase chargeBalanceUseCase, GetUserBalanceUseCase getUserBalanceUseCase, DeductBalanceUseCase deductBalanceUseCase) {
        this.chargeBalanceUseCase = chargeBalanceUseCase;
        this.getUserBalanceUseCase = getUserBalanceUseCase;
        this.deductBalanceUseCase = deductBalanceUseCase;
    }

    // 잔액 충전 API
    @PostMapping("/charge")
    public ResponseEntity<ChargeResponse> chargeBalance(@RequestBody ChargeRequest request) {
        logger.info("잔액충전 request for userId: {} amount: {}", request.getUserId(), request.getAmount());
        ChargeInput chargeInput = new ChargeInput(request.getUserId(), request.getAmount());
        ChargeOutput chargeOutput = chargeBalanceUseCase.chargeBalance(chargeInput.getUserId(), chargeInput.getAmount());

        ChargeResponse response = new ChargeResponse(chargeOutput.getUserID(), chargeInput.getAmount(), chargeOutput.getNewBalance());
        return ResponseEntity.ok(response);
    }

    // 잔액 차감 API
    @PostMapping("/deduct")
    public ResponseEntity<ChargeResponse> deductBalance(@RequestBody ChargeRequest request) {
        logger.info("잔액 차감 request for userId: {} amount: {}", request.getUserId(), request.getAmount());
        ChargeInput chargeInput = new ChargeInput(request.getUserId(), request.getAmount());
        ChargeOutput chargeOutput = deductBalanceUseCase.deductBalance(chargeInput.getUserId(), chargeInput.getAmount());

        ChargeResponse response = new ChargeResponse(chargeOutput.getUserID(), chargeInput.getAmount(), chargeOutput.getNewBalance());
        return ResponseEntity.ok(response);
    }

    // 잔액 조회 API
    @GetMapping("/{userId}/balance")
    public ResponseEntity<UserResponse> getUserBalance(@PathVariable UUID userId) {
        UserOutput userOutput = getUserBalanceUseCase.getUserById(userId);

        UserResponse response = new UserResponse(userOutput.getUserId(), userOutput.getName(), userOutput.getBalance());
        return ResponseEntity.ok(response);
    }
}
