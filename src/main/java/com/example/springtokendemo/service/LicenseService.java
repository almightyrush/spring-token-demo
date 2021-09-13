package com.example.springtokendemo.service;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.model.dto.DriverLicense;
import com.example.springtokendemo.model.dto.LicenseResponseDto;
import com.example.springtokendemo.repository.LicenseRepo;
import jdk.nashorn.internal.ir.Block;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {

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
        DriverLicense license = idScanService.getLicense(text);

        Optional<BlockedLicenses> blockedLicensesOptional = licenseRepo.findByLicenseIgnoreCase(license.getLicenseNumber());
        if (blockedLicensesOptional.isPresent()) {
            BlockedLicenses blockedLicenses = blockedLicensesOptional.get();
            return new LicenseResponseDto(license.getFullName(), license.getLicenseNumber(), license.getCity(), true, blockedLicenses.getId());
        }
        return new LicenseResponseDto(license.getFullName(), license.getLicenseNumber(), license.getCity(), false);
    }

    public String deleteBlockedLicense(Long id) {
        licenseRepo.delete(licenseRepo.findById(id).orElseThrow(() -> new RuntimeException("License Id cannot be found")));
        return "License unblocked successfully";
    }
}
