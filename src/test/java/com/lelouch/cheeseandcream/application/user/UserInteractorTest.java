package com.lelouch.cheeseandcream.application.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lelouch.cheeseandcream.application.user.command.DeleteUserCommand;
import com.lelouch.cheeseandcream.application.user.command.PasswordEncoderPort;
import com.lelouch.cheeseandcream.application.user.command.SaveUserCommand;
import com.lelouch.cheeseandcream.application.user.query.FindUserById;
import com.lelouch.cheeseandcream.application.user.query.FindUserByUsername;
import com.lelouch.cheeseandcream.domain.User;
import com.lelouch.cheeseandcream.domain.exception.UnauthorizedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class UserInteractorTest {

    @Test
    void updateTargetsThePathIdInsteadOfLookingUpTheNewUsername() {
        InMemoryUsers users = new InMemoryUsers();
        users.save(User.create(7L, "old", "encoded:old-password"));
        UserInteractor interactor = interactor(users);

        interactor.updateUser(7L, "new", "new-password");

        User updated = users.findById(7L).orElseThrow();
        assertEquals("new", updated.getUsername());
        assertEquals("encoded:new-password", updated.getPassword());
    }

    @Test
    void loginRejectsAnInvalidPassword() {
        InMemoryUsers users = new InMemoryUsers();
        users.save(User.create(1L, "admin", "encoded:correct"));

        assertThrows(UnauthorizedException.class, () -> interactor(users).login("admin", "incorrect"));
    }

    private UserInteractor interactor(InMemoryUsers users) {
        PasswordEncoderPort encoder = new PasswordEncoderPort() {
            @Override
            public String encode(String rawPassword) {
                return "encoded:" + rawPassword;
            }

            @Override
            public boolean matches(String rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
        return new UserInteractor(users, users, users, users, encoder, () -> "api-key", LoginResponse::new);
    }

    private static final class InMemoryUsers implements SaveUserCommand, DeleteUserCommand, FindUserById, FindUserByUsername {
        private final Map<Long, User> users = new HashMap<>();

        @Override
        public void save(User user) {
            users.put(user.getId(), user);
        }

        @Override
        public void deleteById(Long id) {
            users.remove(id);
        }

        @Override
        public Optional<User> findById(Long id) {
            return Optional.ofNullable(users.get(id));
        }

        @Override
        public Optional<User> findByUsername(String username) {
            return users.values().stream().filter(user -> username.equals(user.getUsername())).findFirst();
        }
    }
}
