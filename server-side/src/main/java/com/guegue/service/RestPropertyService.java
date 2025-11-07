package com.guegue.service;

import com.guegue.dao.UserDao;
import com.guegue.dao.PropertyDao;
import com.guegue.model.Property;
import com.guegue.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Service
public class RestPropertyService implements PropertyService{

    private PropertyDao propertyDao;
    private UserDao userDao;

    public RestPropertyService (PropertyDao propertyDao, UserDao userDao){
        this.propertyDao = propertyDao;
        this.userDao = userDao;
    }


    private boolean isAdminUser(User user) {
        return user.getRole().equalsIgnoreCase("ROLE_ADMIN");
    }

    private User getUser(Principal principal){
        return userDao.getUserByUsername(principal.getName());
    }


    @Override
    public Property getPropertyByID(int id, Principal principal) {
        User user = getUser(principal);

        if (isAdminUser(user)){
            return propertyDao.getPropertyByID(id);
        }
        else{
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<Property> getProperties(Principal principal) {
        User user = getUser(principal);


        if (isAdminUser(user)){
            return propertyDao.getProperties();
        }
        else{
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Property updateProperty(Property updatedProperty, Principal principal) {
        User user = getUser(principal);

        if (isAdminUser(user)){
            return propertyDao.updateProperty(updatedProperty);
        }
        else{
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Property createProperty(Property newProperty, Principal principal) {
        User user = getUser(principal);

        if (isAdminUser(user)){
            return propertyDao.createProperty(newProperty);
        }
        else{
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int deletePropertyByID(int id, Principal principal) {
        User user = getUser(principal);

        if (isAdminUser(user)){
            return propertyDao.deletePropertyByID(id);
        }
        else{
            throw new AccessDeniedException("Access denied");
        }
    }
}
