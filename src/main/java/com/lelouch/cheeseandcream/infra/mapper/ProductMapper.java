package com.lelouch.cheeseandcream.infra.mapper;

import com.lelouch.cheeseandcream.infra.orm.product.ProductEntity;
import com.lelouch.cheeseandcream.application.model.product.ProductRequest;
import com.lelouch.cheeseandcream.application.model.product.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName" , source = "categoryEntity.name")
    @Mapping(target = "agentName" , source = "agentEntity.name")
    ProductResponse toResponse(ProductEntity productEntity);

    ProductEntity toEntity(ProductRequest productRequest);

}
