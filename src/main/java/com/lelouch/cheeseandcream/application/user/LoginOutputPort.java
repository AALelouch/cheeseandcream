package com.lelouch.cheeseandcream.application.user;


public interface LoginOutputPort {
    LoginResponse present(String apiKey);
}
