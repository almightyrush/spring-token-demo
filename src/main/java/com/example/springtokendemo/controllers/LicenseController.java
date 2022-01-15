package com.example.springtokendemo.controllers;

import com.example.springtokendemo.model.BlockedLicenses;
import com.example.springtokendemo.model.dto.LicenseResponseDto;
import com.example.springtokendemo.model.dto.LicenseRequestDto;
import com.example.springtokendemo.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PostMapping("/blockLicense")
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

    @GetMapping(value= "/blockLicense")
    public ResponseEntity<List<BlockedLicenses>> getAllBlockLicense() {
        try {
            return new ResponseEntity<>(licenseService.getAllBlockedLicenses(), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/license/check")
    public ResponseEntity<LicenseResponseDto> check(@RequestBody LicenseRequestDto request) {
        try {
            return new ResponseEntity<>(licenseService.licenseCheck(request), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/blockLicense")
    public ResponseEntity<String> unblockLicense(@RequestParam Long licenseId){
        try {
            return new ResponseEntity<>(licenseService.deleteBlockedLicense(licenseId), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("status", "false");
            map.put("message", e.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }
    }
}
