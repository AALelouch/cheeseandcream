package com.lelouch.cheeseandcream.application.operatingcost;

import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostResponse;
import com.lelouch.cheeseandcream.domain.OperatingCost;
import org.springframework.data.domain.Page;

public interface OperatingCostOutputPort {

    Page<OperatingCostResponse> mapToResponse(Page<OperatingCost> financialOperations);

}
