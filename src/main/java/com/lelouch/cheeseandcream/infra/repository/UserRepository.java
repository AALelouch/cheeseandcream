package com.lelouch.cheeseandcream.infra.repository;

import com.lelouch.cheeseandcream.infra.orm.user.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUsername(String username);

}
