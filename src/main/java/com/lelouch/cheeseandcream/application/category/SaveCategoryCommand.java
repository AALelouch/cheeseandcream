package com.lelouch.cheeseandcream.application.category;

import com.lelouch.cheeseandcream.domain.Category;

public interface SaveCategoryCommand {

    void save(Category category);
}
