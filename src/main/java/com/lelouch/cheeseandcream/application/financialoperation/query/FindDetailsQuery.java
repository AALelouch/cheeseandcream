package com.lelouch.cheeseandcream.application.financialoperation.query;

import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationDetailsResponse;
import java.util.List;

public interface FindDetailsQuery {

    List<FinancialOperationDetailsResponse.ProductResponse> findDetailsById(Long id);

}
