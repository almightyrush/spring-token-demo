package com.example.springtokendemo.model.dto;

import lombok.Data;

@Data
public class LicenseResponseDto {

    private String fullName;

    private String licenseNumber;

    private String city;

    public LicenseResponseDto(String fullName, String licenseNumber, String city) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
        this.city = city;
    }
}
