package com.lelouch.cheeseandcream.application.category.query;

public interface CategoryNameExistsQuery {

    boolean exists(String name, Long excludedId);
}
