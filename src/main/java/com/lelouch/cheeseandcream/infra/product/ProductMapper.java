package com.lelouch.cheeseandcream.infra.product;

import com.lelouch.cheeseandcream.application.product.ProductResponse;
import com.lelouch.cheeseandcream.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "agentName", source = "agent.name")
    ProductResponse toResponse(Product product);
}
