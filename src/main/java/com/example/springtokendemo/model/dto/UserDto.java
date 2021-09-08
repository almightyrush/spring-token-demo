package com.example.springtokendemo.model.dto;

import com.example.springtokendemo.model.Restaurant;

public class UserDto {

    private Long id;

    private Restaurant restaurant;

    private String firstName;

    private String lastName;

    private String userName;

    private Boolean isActive;

    public UserDto(Long id, Restaurant restaurant, String firstName, String lastName, String userName) {
        this.id = id;
        this.restaurant = restaurant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }


}
