package com.lelouch.cheeseandcream.application.user;

import com.lelouch.cheeseandcream.domain.User;

public interface SaveUserCommand {
    void save(User user);
}
