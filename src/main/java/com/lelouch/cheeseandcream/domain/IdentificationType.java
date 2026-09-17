package com.lelouch.cheeseandcream.domain;

public record IdentificationType(Long id, String name, boolean active) {

    public static IdentificationType create(String name) {
        return new IdentificationType(null, name, true);
    }

    public IdentificationType rename(String name) {
        return new IdentificationType(id, name, active);
    }

    public IdentificationType deactivate() {
        return new IdentificationType(id, name, false);
    }
}
