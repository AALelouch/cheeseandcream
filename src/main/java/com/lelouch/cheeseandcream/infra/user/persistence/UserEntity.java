package com.lelouch.cheeseandcream.infra.user.persistence;

import com.lelouch.cheeseandcream.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public User toDomain() {
        return User.create(id, username, password);
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(user.getId(), user.getUsername(), user.getPassword());
    }

}
