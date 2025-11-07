package com.guegue.model;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Property {
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private int bedroom;
    @NotBlank
    private int bathroom;
    @NotBlank
    private int sqft;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private BigDecimal arv;
    @NotBlank
    private int daysOnMarket;
    private String imageUrl;
    private LocalDateTime createdAt;

    public Property (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public int getSqft() {
        return sqft;
    }

    public void setSqft(int sqft) {
        this.sqft = sqft;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getArv() {
        return arv;
    }

    public void setArv(BigDecimal arv) {
        this.arv = arv;
    }

    public int getDaysOnMarket() {
        return daysOnMarket;
    }

    public void setDaysOnMarket(int daysOnMarket) {
        this.daysOnMarket = daysOnMarket;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return id == property.id && bedroom == property.bedroom && bathroom == property.bathroom && sqft == property.sqft && daysOnMarket == property.daysOnMarket && Objects.equals(title, property.title) && Objects.equals(type, property.type) && Objects.equals(status, property.status) && Objects.equals(address, property.address) && Objects.equals(city, property.city) && Objects.equals(state, property.state) && Objects.equals(price, property.price) && Objects.equals(arv, property.arv) && Objects.equals(imageUrl, property.imageUrl) && Objects.equals(createdAt, property.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, status, address, city, state, bedroom, bathroom, sqft, price, arv, daysOnMarket, imageUrl, createdAt);
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", bedroom=" + bedroom +
                ", bathroom=" + bathroom +
                ", sqft=" + sqft +
                ", price=" + price +
                ", arv=" + arv +
                ", daysOnMarket=" + daysOnMarket +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
