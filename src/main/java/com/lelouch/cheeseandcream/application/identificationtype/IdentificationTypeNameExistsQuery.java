package com.lelouch.cheeseandcream.application.identificationtype;

public interface IdentificationTypeNameExistsQuery {

    boolean exists(String name, Long excludedId);
}
