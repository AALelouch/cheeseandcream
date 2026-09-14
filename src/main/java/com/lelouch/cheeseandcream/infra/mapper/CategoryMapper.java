package com.lelouch.cheeseandcream.infra.mapper;

import com.lelouch.cheeseandcream.infra.orm.product.CategoryEntity;
import com.lelouch.cheeseandcream.application.model.product.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(CategoryEntity categoryEntity);

}
