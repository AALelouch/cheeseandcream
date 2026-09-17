package com.lelouch.cheeseandcream.domain;

public class User {

    private final Long id;
    private String username;
    private String password;

    private User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User create(Long id, String username, String password) {
        return new User(id, username, password);
    }

    public void updateCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
