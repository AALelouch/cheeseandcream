package com.lelouch.cheeseandcream.application.service;

import com.lelouch.cheeseandcream.application.model.login.LoginResponse;

public interface UserCrudService {

    void createUser(String username, String password);
    void updateUser(String username, String password);
    void deleteUserById(Long id);
    LoginResponse login(String username, String password);

}
