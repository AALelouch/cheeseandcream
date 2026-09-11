package com.lelouch.cheeseandcream.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final String REALM;
    private final String API_KEY;

    public ApiKeyInterceptor(@Value("${spring.application.name}") String realm, @Value("${app.api-key}") String apiKey) {
        this.API_KEY = apiKey;
        this.REALM = realm;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String apiKey = authHeader.substring("Basic ".length()).trim();

            if (apiKey.equals(API_KEY)) {
                return true;
            }
        }

        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"" + REALM + "\"");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return false;
    }
}
