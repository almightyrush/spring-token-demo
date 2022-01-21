package com.example.springtokendemo.payload.request;

import com.example.springtokendemo.model.Restaurant;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String firstName;

    private String lastName;

    @NotNull(message = "Please select the restaurant")
    private Restaurant restaurant;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public SignupRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SignupRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public SignupRequest setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }
}
