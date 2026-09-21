package com.lelouch.cheeseandcream.application.identificationtype.query;

import com.lelouch.cheeseandcream.domain.IdentificationType;
import java.util.Optional;

public interface FindIdentificationTypeByIdQuery {

    Optional<IdentificationType> findById(Long id);
}
