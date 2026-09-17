package com.lelouch.cheeseandcream.application.user;

import com.lelouch.cheeseandcream.domain.User;
import java.util.Optional;

public interface FindUserById {
    Optional<User> findById(Long id);
}
