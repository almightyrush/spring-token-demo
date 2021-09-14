package com.example.springtokendemo.model.dto;

import lombok.Data;

@Data
public class LicenseResponseDto {

    private String fullName;

    private String licenseNumber;

    private String city;

    private Boolean isBlocked;

    private Long licenseId;
    private String reason;

    public LicenseResponseDto(String fullName, String licenseNumber, String city, Boolean isBlocked) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
        this.city = city;
        this.isBlocked = isBlocked;
    }

    public LicenseResponseDto(String fullName, String licenseNumber,
                              String city, Boolean isBlocked, Long licenseId, String reason) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
        this.city = city;
        this.isBlocked = isBlocked;
        this.licenseId = licenseId;
        this.reason = reason;
    }
}
