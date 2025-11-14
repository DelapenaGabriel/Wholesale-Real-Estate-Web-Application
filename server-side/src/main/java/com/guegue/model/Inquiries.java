package com.guegue.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inquiries {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    private String propertyType;
    private int bedrooms;
    private int bathrooms;
    private int sqft;
    private String condition;
    private String timeline;

    private String additionalDetails;
    private String status;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Inquiries() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getSqft() {
        return sqft;
    }

    public void setSqft(int sqft) {
        this.sqft = sqft;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inquiries inquiries = (Inquiries) o;
        return id == inquiries.id && bedrooms == inquiries.bedrooms && bathrooms == inquiries.bathrooms && sqft == inquiries.sqft && Objects.equals(fullName, inquiries.fullName) && Objects.equals(email, inquiries.email) && Objects.equals(phone, inquiries.phone) && Objects.equals(address, inquiries.address) && Objects.equals(city, inquiries.city) && Objects.equals(state, inquiries.state) && Objects.equals(zipCode, inquiries.zipCode) && Objects.equals(propertyType, inquiries.propertyType) && Objects.equals(condition, inquiries.condition) && Objects.equals(timeline, inquiries.timeline) && Objects.equals(additionalDetails, inquiries.additionalDetails) && Objects.equals(status, inquiries.status) && Objects.equals(role, inquiries.role) && Objects.equals(createdAt, inquiries.createdAt) && Objects.equals(updatedAt, inquiries.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, phone, address, city, state, zipCode, propertyType, bedrooms, bathrooms, sqft, condition, timeline, additionalDetails, status, role, createdAt, updatedAt);
    }
}
