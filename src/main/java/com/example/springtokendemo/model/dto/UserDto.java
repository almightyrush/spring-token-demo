package com.example.springtokendemo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserDto
{

    private Long id;

    private RestaurantDto restaurant;

    private String firstName;

    private String lastName;

    private String userName;

    private Boolean isActive;

}
