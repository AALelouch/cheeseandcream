package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.domain.Product;
import java.util.List;

public interface FindProductsById {

    List<Product> findAllById(List<Long> productIds);


}
