package com.lelouch.cheeseandcream.mapper;

import com.lelouch.cheeseandcream.entity.product.Category;
import com.lelouch.cheeseandcream.model.product.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

}
