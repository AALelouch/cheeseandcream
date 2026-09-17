package com.lelouch.cheeseandcream.application.category;

import com.lelouch.cheeseandcream.domain.Category;
import java.util.List;

public interface CategoryOutputPort {

    CategoryResponse mapToResponse(Category category);
    List<CategoryResponse> mapToResponse(List<Category> categories);
}
