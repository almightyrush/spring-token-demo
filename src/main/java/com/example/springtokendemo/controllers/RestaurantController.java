package com.example.springtokendemo.controllers;

import com.example.springtokendemo.model.Restaurant;
import com.example.springtokendemo.model.dto.RestaurantRequest;
import com.example.springtokendemo.model.dto.RestaurantResponse;
import com.example.springtokendemo.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class RestaurantController
{

    private final RestaurantService restaurantService;


    public RestaurantController(RestaurantService restaurantService)
    {
        this.restaurantService = restaurantService;
    }


    @PostMapping("/restaurant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponse> saveRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest)
    {
        return new ResponseEntity<>(restaurantService.save(restaurantRequest), HttpStatus.OK);
    }


    @GetMapping("/restaurant")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Restaurant>> findAllRestaurant()
    {
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }
}
