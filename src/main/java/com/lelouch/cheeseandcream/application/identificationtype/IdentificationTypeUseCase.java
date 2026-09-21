package com.lelouch.cheeseandcream.application.identificationtype;

import com.lelouch.cheeseandcream.application.identificationtype.dto.IdentificationTypeRequest;
import com.lelouch.cheeseandcream.application.identificationtype.dto.IdentificationTypeResponse;
import java.util.List;

public interface IdentificationTypeUseCase {

    void createIdentificationType(IdentificationTypeRequest request);
    void updateIdentificationType(Long id, IdentificationTypeRequest request);
    void deleteIdentificationType(Long id);
    List<IdentificationTypeResponse> getAllIdentificationTypes();
}
