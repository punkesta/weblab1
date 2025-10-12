package com.punksoft.weblab1.inventory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemService(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public List<InventoryItemEntity> getItemsByOwnerId(int ownerId) {
        return inventoryItemRepository.findByOwnerId(ownerId);
    }

    public void addItem(int ownerId, String baseItemId) {
        inventoryItemRepository.addItem(ownerId, baseItemId);
    }
}

