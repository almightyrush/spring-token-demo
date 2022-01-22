package com.example.springtokendemo.model.dto;

import lombok.Data;

@Data
public class PinChangeDto
{

    private String pin;

    private String newPin;

    private Long restaurantId;

    private Long userId;
}
