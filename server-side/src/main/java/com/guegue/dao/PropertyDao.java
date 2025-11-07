package com.guegue.dao;

import com.guegue.model.Property;

import java.util.List;

public interface PropertyDao {
    Property getPropertyByID(int id);

    List<Property> getProperties();

    Property updateProperty(Property updatedProperty);

    Property createProperty(Property newProperty);

    int deletePropertyByID(int id);
}
