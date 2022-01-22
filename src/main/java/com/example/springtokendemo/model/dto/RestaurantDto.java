package com.example.springtokendemo.model.dto;

import com.example.springtokendemo.model.Restaurant;
import lombok.Data;

@Data
public class RestaurantDto
{

    private Long id;

    private String name;

    private String address1;

    private String address2;

    private String city;

    private String zipCode;

    private String country;

    private String scannerType;

    private Boolean isActive;


    public RestaurantDto(Restaurant restaurant)
    {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.address1 = restaurant.getAddress1();
        this.address2 = restaurant.getAddress2();
        this.city = restaurant.getCity();
        this.zipCode = restaurant.getZipCode();
        this.country = restaurant.getCountry();
        this.scannerType = restaurant.getScannerType();
        this.isActive = restaurant.getActive();
    }
}
