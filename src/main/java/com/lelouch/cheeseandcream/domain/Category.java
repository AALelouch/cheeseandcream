package com.lelouch.cheeseandcream.domain;

public record Category(Long id, String name, boolean active) {

    public static Category create(String name) {
        return new Category(null, name, true);
    }

    public Category rename(String name) {
        return new Category(id, name, active);
    }

    public Category deactivate() {
        return new Category(id, name, false);
    }
}
