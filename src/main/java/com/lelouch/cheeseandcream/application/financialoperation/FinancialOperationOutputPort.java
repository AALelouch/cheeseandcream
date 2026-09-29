package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationRequest;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationResponse;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import java.util.HashMap;
import org.springframework.data.domain.Page;

public interface FinancialOperationOutputPort {

    Page<FinancialOperationResponse> mapToResponse(Page<FinancialOperation> financialOperations);

}
