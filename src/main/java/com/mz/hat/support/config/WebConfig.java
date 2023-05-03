package com.mz.hat.support.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HandlerInterceptor UserInterceptor;

    public WebConfig(HandlerInterceptor userInterceptor) {
        UserInterceptor = userInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(UserInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns();
    }
}
