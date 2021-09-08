package com.example.springtokendemo.service;

import com.example.springtokendemo.model.Restaurant;
import com.example.springtokendemo.repository.RestaurantRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepo restaurantRepo;

    public RestaurantService(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepo.findAll();
    }
}
