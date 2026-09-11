package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.model.login.LoginResponse;
import com.lelouch.cheeseandcream.service.UserCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserCrudService userCrudService;

    public LoginController(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(String username, String password) {
        LoginResponse response = userCrudService.login(username, password);
        return ResponseEntity.ok(response);
    }
}
