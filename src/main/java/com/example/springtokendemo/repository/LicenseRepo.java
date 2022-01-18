package com.example.springtokendemo.repository;

import com.example.springtokendemo.model.BlockedLicenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepo extends JpaRepository<BlockedLicenses, Long> {

    Optional<BlockedLicenses> findByLicenseIgnoreCase(String license);

    @Query("SELECT b FROM BlockedLicenses b WHERE b.license LIKE lower(concat('%', concat(:query, '%'))) "
        + "or b.fullName LIKE lower(concat('%', concat(:query, '%'))) "
        + "or b.city LIKE lower(concat('%', concat(:query, '%')))")
    List<BlockedLicenses> findByFullNameContainingOrLicenseContainingOrCityContaining(String query);
}
