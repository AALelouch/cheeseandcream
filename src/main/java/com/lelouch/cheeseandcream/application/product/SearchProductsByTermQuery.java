package com.lelouch.cheeseandcream.application.product;

import com.lelouch.cheeseandcream.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductsByTermQuery {

    Page<Product> searchByTerm(Long agentId, ProductTermRequest term, Pageable pageable);
}
