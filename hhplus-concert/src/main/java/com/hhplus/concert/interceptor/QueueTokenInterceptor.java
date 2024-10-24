package com.hhplus.concert.interceptor;

import com.hhplus.concert.domain.queue.Queue;
import com.hhplus.concert.infrastructure.repository.QueueRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
public class QueueTokenInterceptor implements HandlerInterceptor {

    private final QueueRepository queueRepository;

    public QueueTokenInterceptor(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader("Queue-Token");
        String userIdString = request.getHeader("User-Id");

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("대기열 토큰이 없거나 유효하지 않습니다.");
            return false;
        }

        if (userIdString == null || userIdString.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("사용자 ID가 없거나 유효하지 않습니다.");
            return false;
        }

        UUID userId = UUID.fromString(userIdString);
        Queue queue = queueRepository.findByTokenAndUserId(token, userId)
                .orElseThrow(() -> new NoSuchElementException("토큰 또는 사용자 정보가 유효하지 않습니다."));

        if(!queue.getStatus().equals("ACTIVE")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("유효하지 않거나 만료된 대기열 토큰입니다.");
            return false;
        }
        return true;
    }
}
