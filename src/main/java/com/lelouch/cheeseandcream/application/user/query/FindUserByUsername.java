package com.lelouch.cheeseandcream.application.user.query;

import com.lelouch.cheeseandcream.domain.User;
import java.util.Optional;

public interface FindUserByUsername {
    Optional<User> findByUsername(String username);
}
