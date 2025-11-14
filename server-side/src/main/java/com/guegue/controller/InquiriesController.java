package com.guegue.controller;

import com.guegue.exception.DaoException;
import com.guegue.model.Inquiries;
import com.guegue.service.InquiriesService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/inquiries")
@RestController
public class InquiriesController {
    private InquiriesService inquiriesService;

    public InquiriesController(InquiriesService inquiriesService){
        this.inquiriesService = inquiriesService;
    }

    @GetMapping("")
    public List<Inquiries> getInquiries(){
        try{
            return inquiriesService.getInquiries();
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
    }

    // 🟦 GET single inquiry by ID
    @GetMapping("/{id}")
    public Inquiries getInquiry(@PathVariable int id) {
        try {
            Inquiries inquiry = inquiriesService.getInquiryById(id);
            if (inquiry == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inquiry not found");
            }
            return inquiry;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // 🟨 POST - Create a new inquiry
    @PreAuthorize("permitAll()")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Inquiries createInquiry(@RequestBody Inquiries newInquiry) {
        try {
            return inquiriesService.createInquiry(newInquiry);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // 🟧 PUT - Update an existing inquiry
    @PutMapping("/{id}")
    public Inquiries updateInquiry(@PathVariable int id, @RequestBody Inquiries updatedInquiry) {
        try {
            updatedInquiry.setId(id);
            Inquiries result = inquiriesService.updateInquiry(updatedInquiry);
            if (result == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inquiry not found");
            }
            return result;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // 🟥 DELETE - Delete inquiry by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInquiry(@PathVariable int id) {
        try {
            int rowsAffected = inquiriesService.deleteInquiry(id);
            if (rowsAffected == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inquiry not found");
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
