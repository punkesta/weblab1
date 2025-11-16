package com.noname.weblabs.inventory.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import com.noname.weblabs.inventory.repo.InventoryItemRepository;
import com.noname.weblabs.user.UserRepository;

@Service
public class SellService {
    private final InventoryItemRepository inventoryItemRepository;
    private final UserRepository userRepository;

    public SellService(InventoryItemRepository inventoryItemRepository, UserRepository userRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public double sellItem(int itemId) {
        var item = inventoryItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        var user = userRepository.findById(item.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var price = item.getBaseItem().getPrice();
        user.setGoldBalance(user.getGoldBalance() + price);

        userRepository.save(user);
        inventoryItemRepository.delete(item);

        return item.getBaseItem().getPrice();
    }
}
