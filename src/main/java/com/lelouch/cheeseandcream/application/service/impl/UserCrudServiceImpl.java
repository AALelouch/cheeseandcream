package com.lelouch.cheeseandcream.application.service.impl;

import com.lelouch.cheeseandcream.infra.orm.user.UserEntity;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.domain.exception.UnauthorizedException;
import com.lelouch.cheeseandcream.application.model.login.LoginResponse;
import com.lelouch.cheeseandcream.infra.repository.UserRepository;
import com.lelouch.cheeseandcream.application.service.UserCrudService;
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
        userRepository.save(new UserEntity(0L, username, base64Encoder.encodeToString(password.getBytes())));
    }

    @Override
    public void updateUser(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("UserEntity not found"));
        userEntity.setPassword(base64Encoder.encodeToString(password.getBytes()));
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("UserEntity not found"));
        userRepository.deleteById(id);
    }

    @Override
    public LoginResponse login(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("Credential errors"));
        if (userEntity.getPassword().equals(base64Encoder.encodeToString(password.getBytes()))) {
            return new LoginResponse(apiKey);
        }
        throw new UnauthorizedException("Credential errors");
    }


}
