package com.example.springtokendemo.service;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.model.dto.BlockedLicenseResponse;
import com.example.springtokendemo.model.dto.LicenseRequestDto;
import com.example.springtokendemo.model.dto.LicenseResponseDto;
import com.example.springtokendemo.model.dto.UserDto;
import com.example.springtokendemo.repository.LicenseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenseService
{

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private final LicenseRepo licenseRepo;


    public LicenseService(LicenseRepo licenseRepo)
    {
        this.licenseRepo = licenseRepo;
    }


    private static LicenseResponseDto generateResponse(BlockedLicenses l)
    {
        return new LicenseResponseDto(l.getFullName(), l.getLicense(), l.getAddress1(),
            l.getAddress2(), l.getCountry(), l.getCity(), l.getPostalCode(), true, l.getId(), l.getReason());
    }


    public BlockedLicenseResponse blockLicense(BlockedLicenses blockedLicensesRequest) throws Exception
    {
        Optional<BlockedLicenses> blockedLicenses = licenseRepo.findByLicenseIgnoreCase(blockedLicensesRequest.getLicense());
        if (blockedLicenses.isPresent())
        {
            throw new Exception("License is already blocked");
        }
        return blockedLicenseResponseBuilder(licenseRepo.save(blockedLicensesRequest));
    }


    public List<BlockedLicenseResponse> getAllBlockedLicenses()
    {
        return licenseRepo.findAllByOrderByCreatedAtDesc()
            .stream().limit(10)
            .collect(Collectors.toList())
            .stream()
            .map(l -> blockedLicenseResponseBuilder(l))
            .collect(Collectors.toList());
    }


    public LicenseResponseDto licenseCheck(LicenseRequestDto request)
    {
        return licenseRepo.findByLicenseIgnoreCase(request.getLicenseNo()).map(LicenseService::generateResponse
        ).orElse(
            new LicenseResponseDto(request.getFullName(), request.getLicenseNo(), null,
                null, request.getCountry(), request.getCity(), request.getPostalCode(),
                false, null, null)
        );
    }


    public String deleteBlockedLicense(Long id)
    {
        licenseRepo.delete(licenseRepo.findById(id).orElseThrow(() -> new RuntimeException("License cannot be found")));
        return "License unblocked successfully";
    }


    public List<LicenseResponseDto> search(String query)
    {
        return licenseRepo.findByFullNameContainingOrLicenseContainingOrCityContaining(query)
            .stream()
            .map(e -> generateResponse(e))
            .collect(Collectors.toList());
    }


    private BlockedLicenseResponse blockedLicenseResponseBuilder(BlockedLicenses savedLicense)
    {
        return BlockedLicenseResponse.builder()
            .license(savedLicense.getLicense())
            .address1(savedLicense.getAddress1())
            .address2(savedLicense.getAddress2())
            .id(savedLicense.getId())
            .city(savedLicense.getCity())
            .country(savedLicense.getCountry())
            .fullName(savedLicense.getFullName())
            .reason(savedLicense.getReason())
            .postalCode(savedLicense.getPostalCode())
            .user(UserDto.builder().id(savedLicense.getUser().getId())
                .firstName(savedLicense.getUser().getFirstName())
                .restaurant(savedLicense.getUser().getRestaurant())
                .id(savedLicense.getUser().getId()).build())
            .build();
    }
}
