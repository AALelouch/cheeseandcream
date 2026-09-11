package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.login.LoginResponse;

public interface UserCrudService {

    void createUser(String username, String password);
    void updateUser(String username, String password);
    void deleteUserById(Long id);
    LoginResponse login(String username, String password);

}
