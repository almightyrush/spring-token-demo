package com.example.springtokendemo.model;


import lombok.Data;

import javax.persistence.*;


@Data
@Table(name = "blocked_licenses")
@Entity
public class BlockedLicenses extends AbstractEntity {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private String license;

    private String fullName;

    private String address1;

    private String address2;

    private String country;

    private String city;

    private String postalCode;

    private String reason;

    public BlockedLicenses() {
    }

}
