package com.example.springtokendemo.service;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.model.dto.DriverLicense;
import com.example.springtokendemo.model.dto.LicenseResponseDto;
import com.example.springtokendemo.repository.LicenseRepo;
import com.example.springtokendemo.security.jwt.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private final LicenseRepo licenseRepo;
    private final IdScanService idScanService;



    public LicenseService(LicenseRepo licenseRepo, IdScanService idScanService) {
        this.licenseRepo = licenseRepo;
        this.idScanService = idScanService;
    }

    public BlockedLicenses blockLicense(BlockedLicenses blockedLicensesRequest) throws Exception {
        Optional<BlockedLicenses> blockedLicenses = licenseRepo.findByLicenseIgnoreCase(blockedLicensesRequest.getLicense());
        if (blockedLicenses.isPresent()) {
            throw new Exception("License is already blocked");
        }
        return licenseRepo.save(blockedLicensesRequest);
    }

    public List<BlockedLicenses> getAllBlockedLicenses() {
        return licenseRepo.findAll();
    }

    public LicenseResponseDto parseText(String text) throws Exception {
        logger.info("Parse Text :"+ text+ ":");

        DriverLicense license = idScanService.getLicense(text);

        if(license != null) {
            logger.info("License DTO parsed: "+ license);
            Optional<BlockedLicenses> blockedLicensesOptional = licenseRepo.findByLicenseIgnoreCase(license.getLicenseNumber());
            if (blockedLicensesOptional.isPresent()) {
                BlockedLicenses blockedLicenses = blockedLicensesOptional.get();
                logger.info("License is blocked : "+blockedLicenses);
                return new LicenseResponseDto(license.getFullName(), license.getLicenseNumber(), license.getAddress1(),
                        license.getAddress2(), license.getCountry(), license.getCity(), license.getPostalCode(), true, blockedLicenses.getId(), blockedLicenses.getReason());
            }
        }
        logger.info("License is not blocked");
        return new LicenseResponseDto(license.getFullName(), license.getLicenseNumber(), license.getAddress1(),
                license.getAddress2(), license.getCountry(), license.getCity(), license.getPostalCode(), false, null, null);
    }

    public String deleteBlockedLicense(Long id) {
        licenseRepo.delete(licenseRepo.findById(id).orElseThrow(() -> new RuntimeException("License Id cannot be found")));
        return "License unblocked successfully";
    }
}
