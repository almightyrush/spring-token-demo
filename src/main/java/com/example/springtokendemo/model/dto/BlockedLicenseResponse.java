package com.example.springtokendemo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockedLicenseResponse
{

    private Long id;

    private UserDto user;

    private String license;

    private String fullName;

    private String address1;

    private String address2;

    private String country;

    private String city;

    private String postalCode;

    private String reason;
}
