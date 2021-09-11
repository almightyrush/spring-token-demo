package com.example.springtokendemo.controllers;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.model.dto.Request;
import com.example.springtokendemo.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/blockLicense", method = RequestMethod.POST)
    public ResponseEntity<BlockedLicenses> blockLicense(@RequestBody BlockedLicenses blockedLicenses) {
        try {
            return new ResponseEntity<>(licenseService.blockLicense(blockedLicenses), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST)
    public ResponseEntity<String> parse(@RequestBody Request request) {
        try {
            return new ResponseEntity<>(licenseService.parseText(request.getText()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
