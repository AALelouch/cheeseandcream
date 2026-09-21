package com.lelouch.cheeseandcream.application.financialoperation.query;

import com.lelouch.cheeseandcream.domain.Product;
import java.util.List;

public interface FindProductsByIdQuery {

    List<Product> findAllById(List<Long> productIds);


}
