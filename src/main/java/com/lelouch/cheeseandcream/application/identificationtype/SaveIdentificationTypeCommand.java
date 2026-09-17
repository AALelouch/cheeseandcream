package com.lelouch.cheeseandcream.application.identificationtype;

import com.lelouch.cheeseandcream.domain.IdentificationType;

public interface SaveIdentificationTypeCommand {

    void save(IdentificationType identificationType);
}
