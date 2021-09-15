package com.example.springtokendemo.model.dto;

import lombok.Data;

@Data
public class LicenseResponseDto {

    private String fullName;

    private String licenseNumber;

    private String address1;

    private String address2;

    private String country;

    private String city;

    private String postalCode;

    private Boolean isBlocked;

    private Long licenseId;

    public LicenseResponseDto(String fullName, String licenseNumber, String address1, String address2,
                              String country, String city, String postalCode, Boolean isBlocked, Long licenseId) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.country = country;
        this.postalCode = postalCode;
        this.city = city;
        this.isBlocked = isBlocked;
        this.licenseId = licenseId;
    }

}
