package com.noname.weblabs.login;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.noname.weblabs.JsonExtKt;
import com.noname.weblabs.security.systemuser.Role;
import com.noname.weblabs.user.UserEntity;
import com.noname.weblabs.user.UserRepository;

@Service
public class RegistrationService {

    private static final Logger LOG = LogManager.getLogger(RegistrationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Name " + username + " already registered.");
        }

        var user = new UserEntity();
        user.setName("New User");
        user.setCoinBalance(1000.0);
        user.setGoldBalance(1000.0);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(List.of(Role.ROLE_USER.name()));

        userRepository.saveAndFlush(user);

        var users = userRepository.findAll();

        LOG.debug(
                "Registered new user. Username: " + username + ", password: " + password +
                "\nUsers: " + JsonExtKt.toJson(users)
        );
    }
}