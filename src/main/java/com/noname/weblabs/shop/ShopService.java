package com.noname.weblabs.shop;

import org.springframework.stereotype.Service;

import java.util.List;

import jakarta.transaction.Transactional;

import com.noname.weblabs.inventory.entity.BaseItemEntity;
import com.noname.weblabs.inventory.repo.BaseItemRepository;
import com.noname.weblabs.inventory.service.InventoryItemService;
import com.noname.weblabs.user.UserRepository;

@Service
public class ShopService {
    private final BaseItemRepository baseItemRepository;
    private final InventoryItemService inventoryItemService;
    private final UserRepository userRepository;

    public ShopService(
            BaseItemRepository baseItemRepository,
            InventoryItemService inventoryItemService,
            UserRepository userRepository)
    {
        this.baseItemRepository = baseItemRepository;
        this.inventoryItemService = inventoryItemService;
        this.userRepository = userRepository;
    }

    public List<BaseItemEntity> getAllBaseItems() {
        return baseItemRepository.findAll();
    }

    @Transactional
    public double buyItem(int ownerId, String baseItemId) {
        var user = userRepository.findById(ownerId)
                                 .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var item = baseItemRepository.findById(baseItemId)
                                     .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        if (user.getGoldBalance() < item.getPrice()) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        user.setGoldBalance(user.getGoldBalance() - item.getPrice());
        userRepository.save(user);

        inventoryItemService.addItem(ownerId, baseItemId);

        return item.getPrice();
    }
}

