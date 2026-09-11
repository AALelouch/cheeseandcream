package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
