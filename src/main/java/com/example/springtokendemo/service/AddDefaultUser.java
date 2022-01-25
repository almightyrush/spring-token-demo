package com.example.springtokendemo.service;

import com.example.springtokendemo.model.ERole;
import com.example.springtokendemo.model.Role;
import com.example.springtokendemo.model.User;
import com.example.springtokendemo.repository.RoleRepository;
import com.example.springtokendemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AddDefaultUser
{

    @Value("${idScan.default.admin.username}")
    private String userName;

    @Value("${idScan.default.admin.password}")
    private String password;

    @Autowired
    private PasswordEncoder encoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    private AddDefaultUser(UserRepository userRepository, RoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public void addAdmin()
    {

        if (!userRepository.findByUsername(userName).isPresent())
        {
            Set<Role> roles = new HashSet<>();

            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);

            User user = new User(userName,
                "admin@admin.com",
                encoder.encode(password),
                null, "admin", "admin");

            user.setRoles(roles);
            userRepository.save(user);
            System.out.println("************Admin user added in the DB successfully**************");
        }
        else
        {
            System.out.println("*************Admin user is already exist*****************");
        }
    }

}
