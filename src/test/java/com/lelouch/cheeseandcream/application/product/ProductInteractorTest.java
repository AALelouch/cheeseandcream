package com.lelouch.cheeseandcream.application.product;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.application.product.dto.ProductResponse;
import com.lelouch.cheeseandcream.application.product.dto.ProductTermRequest;
import com.lelouch.cheeseandcream.application.product.query.SearchProductsByTermQuery;
import com.lelouch.cheeseandcream.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ProductInteractorTest {

    @Test
    void searchProductsKeepsTheSelectedAgentScope() {
        SearchProductsByTermQuery query = mock(SearchProductsByTermQuery.class);
        ProductOutputPort outputPort = mock(ProductOutputPort.class);
        ProductTermRequest term = new ProductTermRequest("cream");
        Pageable pageable = PageRequest.of(1, 10);
        Page<Product> products = new PageImpl<>(java.util.List.of());
        Page<ProductResponse> responses = new PageImpl<>(java.util.List.of());
        when(query.searchByTerm(12L, term, pageable)).thenReturn(products);
        when(outputPort.mapToResponse(products)).thenReturn(responses);
        ProductInteractor interactor = new ProductInteractor(null, null, query, null, null, null, null, null,
                outputPort);

        Page<ProductResponse> result = interactor.searchProducts(12L, term, pageable);

        assertSame(responses, result);
        verify(query).searchByTerm(12L, term, pageable);
    }
}
