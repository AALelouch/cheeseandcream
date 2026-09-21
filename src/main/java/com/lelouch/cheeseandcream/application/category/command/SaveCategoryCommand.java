package com.lelouch.cheeseandcream.application.category.command;

import com.lelouch.cheeseandcream.domain.Category;

public interface SaveCategoryCommand {

    void save(Category category);
}
