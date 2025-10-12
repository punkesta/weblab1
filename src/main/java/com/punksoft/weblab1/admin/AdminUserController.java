package com.punksoft.weblab1.admin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import com.punksoft.weblab1.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        var users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/ban/{id}")
    public String banUser(
            @PathVariable int id, Principal principal, RedirectAttributes redirectAttributes
    ) {
        var currentUsername = principal.getName();
        var success = userService.banUser(id, currentUsername);

        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Failed to ban this user.");
        } else {
            redirectAttributes.addFlashAttribute("message", "User successfully banned.");
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/unban/{id}")
    public String unbanUser(@PathVariable int id, RedirectAttributes redirectAttributes) {
        userService.unbanUser(id);
        redirectAttributes.addFlashAttribute("message", "User successfully unbanned.");

        return "redirect:/users";
    }

    @PostMapping("/toggle-ban/{id}")
    public String toggleBan(
            @PathVariable int id,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        var user = userService.getUserById(id);
        if (!user.isBanned()) {
            userService.banUser(id, principal.getName());
        } else {
            userService.unbanUser(id);
        }

        var status = user.isBanned() ? "banned" : "unbanned";
        redirectAttributes.addFlashAttribute("message", "User successfully " + status);

        return "redirect:/admin/users";
    }
}
