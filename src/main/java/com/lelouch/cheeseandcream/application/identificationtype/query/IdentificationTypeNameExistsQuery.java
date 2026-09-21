package com.lelouch.cheeseandcream.application.identificationtype.query;

public interface IdentificationTypeNameExistsQuery {

    boolean exists(String name, Long excludedId);
}
