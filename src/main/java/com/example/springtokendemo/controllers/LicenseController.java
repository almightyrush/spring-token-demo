package com.example.springtokendemo.controllers;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.model.dto.*;
import com.example.springtokendemo.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class LicenseController
{

    private final LicenseService licenseService;


    public LicenseController(LicenseService licenseService)
    {
        this.licenseService = licenseService;
    }


    @PostMapping("/blockLicense")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<BlockedLicenseResponse> blockLicense(@RequestBody BlockedLicenses blockedLicenses)
    {
        try
        {
            return new ResponseEntity<>(licenseService.blockLicense(blockedLicenses), HttpStatus.OK);
        }
        catch (Exception e)
        {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/blockLicense")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<BlockedLicenseResponse>> getAllBlockLicense()
    {
        try
        {
            return new ResponseEntity<>(licenseService.getAllBlockedLicenses(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/license/check")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<LicenseResponseDto> check(@RequestBody LicenseRequestDto request)
    {
        try
        {
            return new ResponseEntity<>(licenseService.licenseCheck(request), HttpStatus.OK);
        }
        catch (Exception e)
        {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/blockLicense/unblock")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<CommonResponse> unblockLicense(@RequestBody PinValidationRequest pinValidationRequest)
    {
        try
        {
            return new ResponseEntity<>(licenseService.deleteBlockedLicense(pinValidationRequest), HttpStatus.OK);
        }
        catch (Exception e)
        {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<LicenseResponseDto>> search(@RequestParam String searchQuery)
    {
        return new ResponseEntity<>(licenseService.search(searchQuery), HttpStatus.OK);
    }
}
