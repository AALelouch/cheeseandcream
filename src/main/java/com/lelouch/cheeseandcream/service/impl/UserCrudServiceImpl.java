package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.entity.user.User;
import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.exception.UnauthorizedException;
import com.lelouch.cheeseandcream.model.login.LoginResponse;
import com.lelouch.cheeseandcream.repository.UserRepository;
import com.lelouch.cheeseandcream.service.UserCrudService;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserCrudServiceImpl implements UserCrudService {

    private final UserRepository userRepository;
    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final String apiKey; // Replace

    public UserCrudServiceImpl(UserRepository userRepository, @Value("${app.api-key}") String apiKey) {
        this.apiKey = apiKey;
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(String username, String password) {
        userRepository.save(new User(0L, username, base64Encoder.encodeToString(password.getBytes())));
    }

    @Override
    public void updateUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        user.setPassword(base64Encoder.encodeToString(password.getBytes()));
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteById(id);
    }

    @Override
    public LoginResponse login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("Credential errors"));
        if (user.getPassword().equals(base64Encoder.encodeToString(password.getBytes()))) {
            return new LoginResponse(apiKey);
        }
        throw new UnauthorizedException("Credential errors");
    }


}
