package com.lelouch.cheeseandcream.infra.product;

import com.lelouch.cheeseandcream.application.product.dto.ProductResponse;
import com.lelouch.cheeseandcream.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toResponse(Product product);
}
