package com.lelouch.cheeseandcream.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String allowedOrigins;
    private final ApiKeyInterceptor apiKeyInterceptor;

    public WebConfig(@Value("${app.allowed-origins}") String allowedOrigins, ApiKeyInterceptor apiKeyInterceptor) {
        this.allowedOrigins = allowedOrigins;
        this.apiKeyInterceptor = apiKeyInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .excludeHttpMethods(HttpMethod.OPTIONS)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**", "/api/login");
    }
}