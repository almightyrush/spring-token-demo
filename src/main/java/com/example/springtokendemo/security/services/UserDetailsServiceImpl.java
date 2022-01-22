package com.example.springtokendemo.security.services;

import com.example.springtokendemo.model.ERole;
import com.example.springtokendemo.model.User;
import com.example.springtokendemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        if (user.getActive())
        {
            Boolean isAdmin = user.getRoles().stream().anyMatch(e -> ERole.ROLE_ADMIN.name().equals(e.getName().name()));
            if (isAdmin)
                return UserDetailsImpl.build(user);
            else if(user.getRestaurant().getActive())
                return UserDetailsImpl.build(user);
            else
                throw new RuntimeException("Hotel is not active");
        }
        else
            throw new UsernameNotFoundException("User is not Active: " + username);

    }

}
