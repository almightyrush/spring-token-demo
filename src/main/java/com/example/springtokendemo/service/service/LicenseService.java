package com.example.springtokendemo.service.service;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.repository.LicenseRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LicenseService {

    private final LicenseRepo licenseRepo;
//    private final IdScanService idScanService;
//
//    public LicenseService(LicenseRepo licenseRepo, IdScanService idScanService) {
//        this.licenseRepo = licenseRepo;
//        this.idScanService = idScanService;
//
//    }

    public LicenseService(LicenseRepo licenseRepo) {
        this.licenseRepo = licenseRepo;
    }
    public BlockedLicenses blockLicense(BlockedLicenses blockedLicensesRequest) throws Exception {
        Optional<BlockedLicenses> blockedLicenses = licenseRepo.findByLicenseIgnoreCase(blockedLicensesRequest.getLicense());
        if (blockedLicenses.isPresent()) {
            throw new Exception("License is already blocked");
        }
        return licenseRepo.save(blockedLicensesRequest);
    }

//    public String parseText(String text) throws Exception {
//        String license = idScanService.getLicense(text);
//
//        Optional<BlockedLicenses> blockedLicenses = licenseRepo.findByLicenseIgnoreCase(license);
//        if (blockedLicenses.isPresent()) {
//            throw new Exception("License is blocked due to :" + blockedLicenses.get().getReason());
//        }
//        return "License is not blocked";
//    }
}
