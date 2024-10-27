package com.hhplus.concert.config;

import com.hhplus.concert.config.interceptor.QueueTokenInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    private final QueueTokenInterceptor queueTokenInterceptor;

    public WebConfig(QueueTokenInterceptor queueTokenInterceptor) {
        this.queueTokenInterceptor = queueTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(queueTokenInterceptor)
                .addPathPatterns("/api/queues/**", "/api/reservations/**")
                .excludePathPatterns("/api/queues/generate-token");
    }
}
