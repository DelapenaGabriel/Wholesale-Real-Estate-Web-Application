package com.guegue.service;

import com.guegue.model.Property;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

public interface PropertyService {
    Property getPropertyByID(int id, Principal principal);

    List<Property> getProperties(Principal principal);

    Property updateProperty(Property updatedProperty, Principal principal);

    Property createProperty(Property newProperty, Principal principal);

    int deletePropertyByID(int id, Principal principal);
}
