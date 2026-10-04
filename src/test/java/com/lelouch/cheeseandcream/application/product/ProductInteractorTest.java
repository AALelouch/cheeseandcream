package com.lelouch.cheeseandcream.application.product;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.lelouch.cheeseandcream.application.product.dto.ProductIdNameResponse;
import com.lelouch.cheeseandcream.application.product.dto.ProductResponse;
import com.lelouch.cheeseandcream.application.product.dto.ProductTermRequest;
import com.lelouch.cheeseandcream.application.product.query.FindProductsByAgentIdQuery;
import com.lelouch.cheeseandcream.application.product.query.SearchProductsByTermQuery;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ProductInteractorTest {

    @Test
    void getProductsReturnsTheProjectedPageWithoutBuildingDomainProducts() {
        Page<ProductResponse> responses = new PageImpl<>(java.util.List.of(
                new ProductResponse(1L, "Queso", 4.0, 8.0, "kg", "Maduro")));
        FindProductsByAgentIdQuery query = (agentId, pageable) -> responses;
        ProductInteractor interactor = new ProductInteractor(null, query, null, null, null, null, null, null,
                null);

        Page<ProductResponse> result = interactor.getProductsByAgentId(12L, PageRequest.of(0, 10));

        assertSame(responses, result);
    }

    @Test
    void searchProductsKeepsTheSelectedAgentScopeAndProjectedResponse() {
        ProductTermRequest term = new ProductTermRequest("cream");
        Pageable pageable = PageRequest.of(1, 10);
        Page<ProductResponse> responses = new PageImpl<>(java.util.List.of());
        SearchProductsByTermQuery query = new SearchProductsByTermQuery() {
            @Override
            public Page<ProductResponse> searchByTerm(Long agentId, ProductTermRequest request,
                    Pageable requestedPage) {
                return agentId.equals(12L) && request == term && requestedPage == pageable
                        ? responses
                        : Page.empty();
            }

            @Override
            public Page<ProductIdNameResponse> searchByTermIdName(Long agentId, ProductTermRequest request,
                    Pageable requestedPage) {
                return Page.empty();
            }
        };
        ProductInteractor interactor = new ProductInteractor(null, null, query, null, null, null, null, null,
                null);

        Page<ProductResponse> result = interactor.searchProducts(12L, term, pageable);

        assertSame(responses, result);
    }
}
