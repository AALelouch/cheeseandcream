package com.lelouch.cheeseandcream.service.mapper;

import com.lelouch.cheeseandcream.entity.Product;
import com.lelouch.cheeseandcream.service.model.product.ProductRequest;
import com.lelouch.cheeseandcream.service.model.product.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse productToResponse(Product product);
    Product productRequestToProduct(ProductRequest productRequest);

}
