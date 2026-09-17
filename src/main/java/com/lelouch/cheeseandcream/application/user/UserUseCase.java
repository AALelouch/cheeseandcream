package com.lelouch.cheeseandcream.application.user;


public interface UserUseCase {

    void createUser(String username, String password);
    void updateUser(Long id, String username, String password);
    void deleteUserById(Long id);
    LoginResponse login(String username, String password);
}
