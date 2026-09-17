package com.lelouch.cheeseandcream.infra.category;

import com.lelouch.cheeseandcream.domain.Category;
import com.lelouch.cheeseandcream.infra.category.persistence.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryJpaMapper {

    @Mapping(target = "rename", ignore = true)
    Category toDomain(CategoryEntity categoryEntity);
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    CategoryEntity toEntity(Category category);
}
