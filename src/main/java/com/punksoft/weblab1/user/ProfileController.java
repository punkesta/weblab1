package com.punksoft.weblab1.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewProfile(Model model, Principal principal) {
        var user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile/view";
    }

    @GetMapping("/edit")
    public String editProfileForm(Model model, Principal principal) {
        var user = userService.getUserByUsername(principal.getName());
        var avatarFiles = userService.getAvailableAvatars();

        model.addAttribute("user", user);
        model.addAttribute("avatarFiles", avatarFiles);

        return "profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @ModelAttribute("user") UserEntity updatedUser,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        userService.updateProfile(principal.getName(), updatedUser);
        redirectAttributes.addFlashAttribute("success", "Successfully updated!");
        return "redirect:/profile";
    }
}