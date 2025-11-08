package com.guegue.controller;


import com.guegue.exception.DaoException;
import com.guegue.model.Property;
import com.guegue.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/property")
@RestController
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public Property getProperty(@PathVariable int id, Principal principal) {
        Property property;

        try {
            property = propertyService.getPropertyByID(id, principal);
            if (property == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return property;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public List<Property> listProperties (Principal principal){
        try{
            return propertyService.getProperties(principal);
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Property addProperty (@Valid @RequestBody Property newProperty, Principal principal, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        try{
            return propertyService.createProperty(newProperty, principal, file);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
    }


    @PutMapping("/{id}")
    public Property updateProperty (@PathVariable int id, @Valid @RequestBody Property updatedProperty, Principal principal){
        updatedProperty.setId(id);
        try{
            return propertyService.updateProperty(updatedProperty, principal);
        }catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"); //Status Code: 404 = The given URL doesn't point to a valid resource
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProperty (@PathVariable int id, Principal principal){
        try{
            int deletedRows = propertyService.deletePropertyByID(id, principal);
            if (deletedRows == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
    }




}
