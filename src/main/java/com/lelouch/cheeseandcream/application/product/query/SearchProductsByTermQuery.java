package com.lelouch.cheeseandcream.application.product.query;

import com.lelouch.cheeseandcream.application.product.dto.ProductIdNameResponse;
import com.lelouch.cheeseandcream.application.product.dto.ProductResponse;
import com.lelouch.cheeseandcream.application.product.dto.ProductTermRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductsByTermQuery {

    Page<ProductResponse> searchByTerm(Long agentId, ProductTermRequest term, Pageable pageable);
    Page<ProductIdNameResponse> searchByTermIdName(Long agentId, ProductTermRequest term, Pageable pageable);

}
