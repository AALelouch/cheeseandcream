package com.lelouch.cheeseandcream.infra.identificationtype.adapter;

import com.lelouch.cheeseandcream.application.identificationtype.IdentificationTypeOutputPort;
import com.lelouch.cheeseandcream.application.identificationtype.dto.IdentificationTypeResponse;
import com.lelouch.cheeseandcream.domain.IdentificationType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IdentificationTypePresenter implements IdentificationTypeOutputPort {

    @Override
    public List<IdentificationTypeResponse> mapToResponse(List<IdentificationType> identificationTypes) {
        return identificationTypes.stream()
                .map(type -> new IdentificationTypeResponse(type.id(), type.name()))
                .toList();
    }
}
