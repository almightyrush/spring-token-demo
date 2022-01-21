package com.example.springtokendemo.model;

import javax.persistence.*;

@Table(name = "restaurant")
@Entity
public class Restaurant extends AbstractEntity
{

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address1;

    private String address2;

    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    private String country;

    private String pin;

    private String scannerType;

    @Column(name = "is_active")
    private Boolean isActive = true;


    public Restaurant()
    {
    }


    public Long getId()
    {
        return id;
    }


    public Restaurant setId(Long id)
    {
        this.id = id;
        return this;
    }


    public String getName()
    {
        return name;
    }


    public Restaurant setName(String name)
    {
        this.name = name;
        return this;
    }


    public String getAddress1()
    {
        return address1;
    }


    public Restaurant setAddress1(String address1)
    {
        this.address1 = address1;
        return this;
    }


    public String getAddress2()
    {
        return address2;
    }


    public Restaurant setAddress2(String address2)
    {
        this.address2 = address2;
        return this;
    }


    public String getCity()
    {
        return city;
    }


    public Restaurant setCity(String city)
    {
        this.city = city;
        return this;
    }


    public String getZipCode()
    {
        return zipCode;
    }


    public Restaurant setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
        return this;
    }


    public String getCountry()
    {
        return country;
    }


    public Restaurant setCountry(String country)
    {
        this.country = country;
        return this;
    }


    public Boolean getActive()
    {
        return isActive;
    }


    public Restaurant setActive(Boolean active)
    {
        isActive = active;
        return this;
    }


    public String getPin()
    {
        return pin;
    }


    public Restaurant setPin(String pin)
    {
        this.pin = pin;
        return this;
    }


    public String getScannerType()
    {
        return scannerType;
    }


    public Restaurant setScannerType(String scannerType)
    {
        this.scannerType = scannerType;
        return this;
    }
}
