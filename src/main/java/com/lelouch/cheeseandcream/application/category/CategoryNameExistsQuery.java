package com.lelouch.cheeseandcream.application.category;

public interface CategoryNameExistsQuery {

    boolean exists(String name, Long excludedId);
}
