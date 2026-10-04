package com.lelouch.cheeseandcream.infra.operatingcost.adapter;

import com.lelouch.cheeseandcream.application.operatingcost.OperatingCostOutputPort;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostResponse;
import com.lelouch.cheeseandcream.domain.OperatingCost;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OperatingCostPresenter implements OperatingCostOutputPort {

    @Override
    public Page<OperatingCostResponse> mapToResponse(Page<OperatingCost> financialOperations) {
        return financialOperations.map(OperatingCostResponse::fromDomain);
    }
}
