package com.lelouch.cheeseandcream.infra.user;

import com.lelouch.cheeseandcream.application.user.LoginResponse;
import com.lelouch.cheeseandcream.application.user.UserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserUseCase userUseCase;

    public LoginController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(String username, String password) {
        LoginResponse response = userUseCase.login(username, password);
        return ResponseEntity.ok(response);
    }
}
