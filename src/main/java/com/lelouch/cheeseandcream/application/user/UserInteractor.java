package com.lelouch.cheeseandcream.application.user;

import com.lelouch.cheeseandcream.application.user.command.DeleteUserCommand;
import com.lelouch.cheeseandcream.application.user.command.PasswordEncoderPort;
import com.lelouch.cheeseandcream.application.user.command.SaveUserCommand;
import com.lelouch.cheeseandcream.application.user.query.ApiKeyProvider;
import com.lelouch.cheeseandcream.application.user.query.FindUserById;
import com.lelouch.cheeseandcream.application.user.query.FindUserByUsername;
import com.lelouch.cheeseandcream.domain.User;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.domain.exception.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserInteractor implements UserUseCase {

    private final SaveUserCommand saveUserCommand;
    private final DeleteUserCommand deleteUserCommand;
    private final FindUserById findUserById;
    private final FindUserByUsername findUserByUsername;
    private final PasswordEncoderPort passwordEncoderPort;
    private final ApiKeyProvider apiKeyProvider;
    private final LoginOutputPort loginOutputPort;

    public UserInteractor(SaveUserCommand saveUserCommand, DeleteUserCommand deleteUserCommand,
            FindUserById findUserById, FindUserByUsername findUserByUsername,
            PasswordEncoderPort passwordEncoderPort, ApiKeyProvider apiKeyProvider,
            LoginOutputPort loginOutputPort) {
        this.saveUserCommand = saveUserCommand;
        this.deleteUserCommand = deleteUserCommand;
        this.findUserById = findUserById;
        this.findUserByUsername = findUserByUsername;
        this.passwordEncoderPort = passwordEncoderPort;
        this.apiKeyProvider = apiKeyProvider;
        this.loginOutputPort = loginOutputPort;
    }

    @Override
    @Transactional
    public void createUser(String username, String password) {
        saveUserCommand.save(User.create(null, username, passwordEncoderPort.encode(password)));
    }

    @Override
    @Transactional
    public void updateUser(Long id, String username, String password) {
        User user = findUserById.findById(id)
                .orElseThrow(() -> new NotFoundException("UserEntity not found"));
        user.updateCredentials(username, passwordEncoderPort.encode(password));
        saveUserCommand.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        findUserById.findById(id).orElseThrow(() -> new NotFoundException("UserEntity not found"));
        deleteUserCommand.deleteById(id);
    }

    @Override
    public LoginResponse login(String username, String password) {
        User user = findUserByUsername.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Credential errors"));
        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Credential errors");
        }
        return loginOutputPort.present(apiKeyProvider.getApiKey());
    }
}
