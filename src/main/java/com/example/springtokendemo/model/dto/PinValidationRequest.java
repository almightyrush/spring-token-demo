package com.example.springtokendemo.model.dto;

import lombok.Data;

@Data
public class PinValidationRequest
{

    private Long restaurantId;

    private Long userId;

    private String pin;

    private Long licenceId;
}
