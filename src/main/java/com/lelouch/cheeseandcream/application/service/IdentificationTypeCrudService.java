package com.lelouch.cheeseandcream.application.service;

import com.lelouch.cheeseandcream.application.model.agent.IdentificationTypeResponse;
import com.lelouch.cheeseandcream.application.model.agent.IdentificationTypeRequest;
import java.util.List;

public interface IdentificationTypeCrudService {

    void createIdentificationType(IdentificationTypeRequest identificationTypeRequest);
    void updateIdentificationType(Long id, IdentificationTypeRequest identificationTypeRequest);
    void deleteIdentificationType(Long id);
    List<IdentificationTypeResponse> getAllIdentificationTypes();

}
