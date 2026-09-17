package com.lelouch.cheeseandcream.application.identificationtype;

import com.lelouch.cheeseandcream.domain.IdentificationType;
import java.util.List;

public interface FindAllIdentificationTypesQuery {

    List<IdentificationType> findAll();
}
