package com.lelouch.cheeseandcream.application.product;

import com.lelouch.cheeseandcream.domain.Product;
import java.util.Optional;

public interface FindProductByIdQuery {

    Optional<Product> findById(Long productId);
}
