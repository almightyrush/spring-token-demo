package com.example.springtokendemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DriverLicense {
    @JsonProperty("Address1")
    public String address1;
    @JsonProperty("Address2")
    public String address2;
    @JsonProperty("Birthdate")
    public String birthdate;
    @JsonProperty("CardRevisionDate")
    public String cardRevisionDate;
    @JsonProperty("City")
    public String city;
    @JsonProperty("ClassificationCode")
    public String classificationCode;
    @JsonProperty("ComplianceType")
    public String complianceType;
    @JsonProperty("Country")
    public String country;
    @JsonProperty("CountryCode")
    public String countryCode;
    @JsonProperty("DocumentType")
    public String documentType;
    @JsonProperty("EndorsementCodeDescription")
    public String endorsementCodeDescription;
    @JsonProperty("EndorsementsCode")
    public String endorsementsCode;
    @JsonProperty("ExpirationDate")
    public String expirationDate;
    @JsonProperty("EyeColor")
    public String eyeColor;
    @JsonProperty("FirstName")
    public String firstName;
    @JsonProperty("FullName")
    public String fullName;
    @JsonProperty("Gender")
    public String gender;
    @JsonProperty("HAZMATExpDate")
    public String hAZMATExpDate;
    @JsonProperty("HairColor")
    public String hairColor;
    @JsonProperty("Height")
    public String height;
    @JsonProperty("IIN")
    public String iIN;
    @JsonProperty("IssueDate")
    public String issueDate;
    @JsonProperty("IssuedBy")
    public String issuedBy;
    @JsonProperty("JurisdictionCode")
    public String jurisdictionCode;
    @JsonProperty("LastName")
    public String lastName;
    @JsonProperty("LicenseNumber")
    public String licenseNumber;
    @JsonProperty("LimitedDurationDocument")
    public String limitedDurationDocument;
    @JsonProperty("MiddleName")
    public String middleName;
    @JsonProperty("NamePrefix")
    public String namePrefix;
    @JsonProperty("NameSuffix")
    public String nameSuffix;
    @JsonProperty("OrganDonor")
    public String organDonor;
    @JsonProperty("PostalBox")
    public String postalBox;
    @JsonProperty("PostalCode")
    public String postalCode;
    @JsonProperty("Race")
    public String race;
    @JsonProperty("RestrictionCode")
    public String restrictionCode;
    @JsonProperty("RestrictionCodeDescription")
    public String restrictionCodeDescription;
    @JsonProperty("VehicleClassCode")
    public String vehicleClassCode;
    @JsonProperty("VehicleClassCodeDescription")
    public String vehicleClassCodeDescription;
    @JsonProperty("Veteran")
    public String veteran;
    @JsonProperty("WeightKG")
    public String weightKG;
    @JsonProperty("WeightLBS")
    public String weightLBS;
}

