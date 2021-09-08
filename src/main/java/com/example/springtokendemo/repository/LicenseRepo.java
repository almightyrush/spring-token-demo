package com.example.springtokendemo.repository;

import com.example.springtokendemo.model.BlockedLicenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LicenseRepo extends JpaRepository<BlockedLicenses, Long> {

    Optional<BlockedLicenses> findByLicenseIgnoreCase(String license);
}
