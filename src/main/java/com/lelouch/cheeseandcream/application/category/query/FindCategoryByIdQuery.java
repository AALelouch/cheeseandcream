package com.lelouch.cheeseandcream.application.category.query;

import com.lelouch.cheeseandcream.domain.Category;
import java.util.Optional;

public interface FindCategoryByIdQuery {

    Optional<Category> findById(Long id);
}
