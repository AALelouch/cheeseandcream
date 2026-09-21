package com.lelouch.cheeseandcream.application.user.command;

import com.lelouch.cheeseandcream.domain.User;

public interface SaveUserCommand {
    void save(User user);
}
