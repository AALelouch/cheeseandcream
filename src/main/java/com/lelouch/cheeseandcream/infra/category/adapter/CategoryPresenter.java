package com.lelouch.cheeseandcream.infra.category.adapter;

import com.lelouch.cheeseandcream.application.category.CategoryOutputPort;
import com.lelouch.cheeseandcream.application.category.dto.CategoryResponse;
import com.lelouch.cheeseandcream.domain.Category;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryPresenter implements CategoryOutputPort {

    @Override
    public CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(category.id(), category.name());
    }

    @Override
    public List<CategoryResponse> mapToResponse(List<Category> categories) {
        return categories.stream().map(this::mapToResponse).toList();
    }
}
