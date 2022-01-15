package com.example.springtokendemo.model.dto;

import com.example.springtokendemo.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantResponse
{

    private boolean isSuccess;
    private String errorMsg;

    private Restaurant restaurant;
}
