package com.punksoft.weblab1.shop;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punksoft.weblab1.security.systemuser.CustomUserDetails;
import com.punksoft.weblab1.user.UserService;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private final UserService userService;

    public ShopController(ShopService shopService, UserService userService) {
        this.shopService = shopService;
        this.userService = userService;
    }

    @GetMapping
    public String showShop(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        var items = shopService.getAllBaseItems();
        model.addAttribute("items", items);
        model.addAttribute("userId", user.getUserId());

        var gold = userService.getUserByUsername(user.getUsername()).getGoldBalance();
        model.addAttribute("gold", gold);

        return "shop/index";
    }

    @PostMapping("/buy")
    public String buyItem(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam String baseItemId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            var price = shopService.buyItem(user.getUserId(), baseItemId);
            redirectAttributes.addFlashAttribute("message", "-" + price + "$");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/shop";
    }
}
