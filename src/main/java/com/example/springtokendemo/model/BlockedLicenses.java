package com.example.springtokendemo.model;


import javax.persistence.*;


@Table(name = "blocked_licenses")
@Entity
public class BlockedLicenses extends AbstractEntity {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private String license;

    private String reason;

    public BlockedLicenses() {
    }

    public Long getId() {
        return id;
    }

    public BlockedLicenses setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BlockedLicenses setUser(User user) {
        this.user = user;
        return this;
    }

    public String getLicense() {
        return license;
    }

    public BlockedLicenses setLicense(String license) {
        this.license = license;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public BlockedLicenses setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
