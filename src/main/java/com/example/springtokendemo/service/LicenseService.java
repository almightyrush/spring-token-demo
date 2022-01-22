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


    public CommonResponse deleteBlockedLicense(PinValidationRequest pinValidationRequest)
    {
        Restaurant restaurant = restaurantRepo.findById(pinValidationRequest.getRestaurantId()).orElseThrow(() -> new RuntimeException("Restaurant cannot be found"));
        User user = userRepository.findById(pinValidationRequest.getUserId()).orElseThrow(() -> new RuntimeException("User cannot be found"));

        if (user.getRestaurant().getId().equals(restaurant.getId()))
        {
            if (user.getActive())
            {
                if (encoder.matches(pinValidationRequest.getPin(), restaurant.getPin()))
                {
                    Optional<BlockedLicenses> blockedLicenses = licenseRepo.findById(pinValidationRequest.getLicenceId());
                    if (blockedLicenses.isPresent())
                    {
                        licenseRepo.delete(blockedLicenses.get());
                    }
                    else
                        return new CommonResponse(true, "License Details not found");
                    return new CommonResponse(true, "License unblocked successfully");
                }
                else
                    return new CommonResponse(false, "Pin does not match");
            }

            return new CommonResponse(false, "User is not Active");
        }
        return new CommonResponse(false, "Other Hotel cannot unblock");
    }


    public List<LicenseResponseDto> search(String query)
    {
        return licenseRepo.findByFullNameContainingOrLicenseContainingOrCityContaining(query)
            .stream()
            .limit(10)
            .map(e -> generateResponse(e))
            .collect(Collectors.toList());
    }


    private BlockedLicenseResponse blockedLicenseResponseBuilder(BlockedLicenses savedLicense)
    {
        try
        {
            User user = userRepository.findById(savedLicense.getUser().getId()).get();
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
                .user(UserDto.builder().id(user.getId())
                    .firstName(user.getFirstName())
                    .restaurant(new RestaurantDto(user.getRestaurant()))
                    .id(user.getId()).build())
                .build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
