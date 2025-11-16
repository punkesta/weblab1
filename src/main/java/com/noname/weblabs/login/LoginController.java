package com.noname.weblabs.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.noname.weblabs.user.UserEntity;

@Controller
public class LoginController {

    private final RegistrationService registrationService;

    public LoginController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        System.out.println("Register form called");
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserEntity user) {
        System.out.println("Register called");
        registrationService.registerUser(user.getUsername(), user.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}