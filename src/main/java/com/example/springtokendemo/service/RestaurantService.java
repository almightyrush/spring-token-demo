package com.example.springtokendemo.service;

import com.example.springtokendemo.model.Restaurant;
import com.example.springtokendemo.model.User;
import com.example.springtokendemo.model.dto.PinChangeDto;
import com.example.springtokendemo.model.dto.RestaurantDto;
import com.example.springtokendemo.model.dto.RestaurantRequest;
import com.example.springtokendemo.model.dto.RestaurantResponse;
import com.example.springtokendemo.payload.response.MessageResponse;
import com.example.springtokendemo.repository.RestaurantRepo;
import com.example.springtokendemo.repository.RoleRepository;
import com.example.springtokendemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.example.springtokendemo.model.ERole.ROLE_MODERATOR;

@Service
public class RestaurantService
{

    @Autowired
    PasswordEncoder encoder;

    private final RestaurantRepo restaurantRepo;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public RestaurantService(
        RestaurantRepo restaurantRepo,
        RoleRepository roleRepository, UserRepository userRepository)
    {
        this.restaurantRepo = restaurantRepo;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public RestaurantResponse save(RestaurantRequest restaurantRequest)
    {
        Optional<User> existingUser = userRepository.findByUsername(restaurantRequest.getUsername());
        if (existingUser.isPresent())
        {
            return new RestaurantResponse(false, "UserName should be unique", null);
        }

        User user = new User(restaurantRequest.getUsername(), restaurantRequest.getEmail(), encoder.encode(restaurantRequest.getPassword()));
        return Optional.ofNullable(roleRepository.findByName(ROLE_MODERATOR)).map(role -> {
            user.setRoles(new HashSet<>(Collections.singletonList(role.get())));
            user.setRestaurant(getRestaurant(restaurantRequest));
            User savedUser = userRepository.save(user);

            return new RestaurantResponse(true, null, new RestaurantDto(savedUser.getRestaurant()));
        }).orElse(
            new RestaurantResponse(false, "Role not found", null)
        );
    }


    private Restaurant getRestaurant(RestaurantRequest restaurantRequest)
    {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setZipCode(restaurantRequest.getZipCode());
        restaurant.setAddress1(restaurantRequest.getAddress1());
        restaurant.setAddress2(restaurantRequest.getAddress2());
        restaurant.setCountry(restaurantRequest.getCountry());
        restaurant.setCity(restaurantRequest.getCity());
        restaurant.setPin(encoder.encode(restaurantRequest.getPin()));
        restaurant.setScannerType(restaurantRequest.getScannerType());
        return restaurant;
    }


    public List<Restaurant> getRestaurants()
    {
        return restaurantRepo.findAll();
    }


    @Transactional
    public MessageResponse pinUpdate(PinChangeDto pinChangeDto)
    {
        Restaurant restaurant = restaurantRepo.findById(pinChangeDto.getRestaurantId()).orElseThrow(() -> new RuntimeException("Hotel not found"));
        if (restaurant.getActive())
        {
            if (encoder.matches(restaurant.getPin(), encoder.encode(pinChangeDto.getPin())))
            {
                restaurant.setPin(encoder.encode(pinChangeDto.getNewPin()));

                restaurantRepo.save(restaurant);
                return new MessageResponse("Pin updated successfully");
            }
            throw new RuntimeException("Entered pin could not be verified");
        }
        throw new RuntimeException("Hotel is not active");
    }
}
