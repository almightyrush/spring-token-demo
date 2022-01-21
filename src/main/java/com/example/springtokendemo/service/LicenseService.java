package com.example.springtokendemo.service;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.model.Restaurant;
import com.example.springtokendemo.model.User;
import com.example.springtokendemo.model.dto.*;
import com.example.springtokendemo.repository.LicenseRepo;
import com.example.springtokendemo.repository.RestaurantRepo;
import com.example.springtokendemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenseService
{

    @Autowired
    PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private final LicenseRepo licenseRepo;
    private final RestaurantRepo restaurantRepo;
    private final UserRepository userRepository;


    public LicenseService(LicenseRepo licenseRepo, UserRepository userRepository, RestaurantRepo restaurantRepo)
    {
        this.licenseRepo = licenseRepo;
        this.userRepository = userRepository;
        this.restaurantRepo = restaurantRepo;
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


    public String deleteBlockedLicense(PinValidationRequest pinValidationRequest)
    {
        Restaurant restaurant = restaurantRepo.findById(pinValidationRequest.getRestaurantId()).orElseThrow(() -> new RuntimeException("Restaurant cannot be found"));
        User user = userRepository.findById(pinValidationRequest.getUserId()).orElseThrow(() -> new RuntimeException("User cannot be found"));

        if (user.getActive())
        {
            if (encoder.matches(encoder.encode(pinValidationRequest.getPin()), restaurant.getPin()))
            {
                licenseRepo.delete(licenseRepo.findById(pinValidationRequest.getLicenceId()).orElseThrow(() -> new RuntimeException("License cannot be found")));
                return "License unblocked successfully";
            }
            else
                throw new RuntimeException("Pin does not match");
        }

        throw new RuntimeException("User is not Active");
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
