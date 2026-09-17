package com.lelouch.cheeseandcream.infra.user.adapter;

import com.lelouch.cheeseandcream.application.user.LoginResponse;
import com.lelouch.cheeseandcream.application.user.LoginOutputPort;
import org.springframework.stereotype.Service;

@Service
public class LoginPresenter implements LoginOutputPort {

    @Override
    public LoginResponse present(String apiKey) {
        return new LoginResponse(apiKey);
    }
}
