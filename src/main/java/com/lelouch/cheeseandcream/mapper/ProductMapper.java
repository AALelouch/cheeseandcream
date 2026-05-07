package com.lelouch.cheeseandcream.mapper;

import com.lelouch.cheeseandcream.entity.product.Product;
import com.lelouch.cheeseandcream.model.product.ProductRequest;
import com.lelouch.cheeseandcream.model.product.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName" , source = "category.name")
    @Mapping(target = "agentName" , source = "agent.name")
    ProductResponse toResponse(Product product);

    Product toEntity( ProductRequest productRequest);

}
