package com.example.springtokendemo.model.dto;

import com.example.springtokendemo.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserDto {

    private Long id;

    private Restaurant restaurant;

    private String firstName;

    private String lastName;

    private String userName;

    private Boolean isActive;




}
