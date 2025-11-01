package com.punksoft.weblab1.inventory.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punksoft.weblab1.inventory.service.InventoryItemService;
import com.punksoft.weblab1.inventory.service.SellService;
import com.punksoft.weblab1.security.systemuser.CustomUserDetails;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryItemService inventoryService;
    private final SellService sellService;

    public InventoryController(InventoryItemService inventoryService, SellService sellService) {
        this.inventoryService = inventoryService;
        this.sellService = sellService;
    }

    @GetMapping
    public String getInventoryByOwner(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        var items = inventoryService.getItemsByOwnerId(user.getUserId());
        model.addAttribute("items", items);
        return "inventory/index";
    }

    @PostMapping("/sell/{itemId}")
    public String sellItem(@PathVariable int itemId, RedirectAttributes redirectAttributes) {
        try {
            var revenue = sellService.sellItem(itemId);
            redirectAttributes.addFlashAttribute("message", "+" + revenue + "$.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/inventory";
    }
}
