package com.example.springtokendemo.model.dto;

import lombok.Data;

@Data
public class LicenseRequestDto
{
    private String licenseNo;

    private String fullName;

    private String city;

    private String country;

    private String postalCode;
}
