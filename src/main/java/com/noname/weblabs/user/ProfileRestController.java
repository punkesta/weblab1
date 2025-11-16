package com.noname.weblabs.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
    @Autowired
    private UserService userService;

    @GetMapping
    public UserEntity getProfile(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }
}