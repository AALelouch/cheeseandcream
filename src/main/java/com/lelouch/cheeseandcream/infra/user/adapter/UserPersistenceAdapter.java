package com.lelouch.cheeseandcream.infra.user.adapter;

import com.lelouch.cheeseandcream.application.user.command.DeleteUserCommand;
import com.lelouch.cheeseandcream.application.user.query.FindUserById;
import com.lelouch.cheeseandcream.application.user.query.FindUserByUsername;
import com.lelouch.cheeseandcream.application.user.command.SaveUserCommand;
import com.lelouch.cheeseandcream.domain.User;
import com.lelouch.cheeseandcream.infra.user.persistence.UserEntity;
import com.lelouch.cheeseandcream.infra.user.persistence.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserPersistenceAdapter implements SaveUserCommand, DeleteUserCommand, FindUserById, FindUserByUsername {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(UserEntity.fromDomain(user));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserEntity::toDomain);
    }
}
