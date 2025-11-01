package com.punksoft.weblab1.inventory.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.punksoft.weblab1.inventory.entity.InventoryItemEntity;
import com.punksoft.weblab1.inventory.repo.BaseItemRepository;
import com.punksoft.weblab1.inventory.repo.InventoryItemRepository;

@Service
public class InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final BaseItemRepository baseItemRepository;

    public InventoryItemService(
            InventoryItemRepository inventoryItemRepository, BaseItemRepository baseItemRepository
    ) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.baseItemRepository = baseItemRepository;
    }

    public InventoryItemEntity getItemById(int id) {
        return inventoryItemRepository.findById(id).orElseThrow();
    }

    public List<InventoryItemEntity> getItemsByOwnerId(int ownerId) {
        return inventoryItemRepository.findByOwnerId(ownerId);
    }

    public InventoryItemEntity addItem(int ownerId, String baseItemId) {
        var baseItem = baseItemRepository.findById(baseItemId).orElseThrow();
        var item = InventoryItemEntity.builder()
                .ownerId(ownerId)
                .baseItem(baseItem)
                .build();

        return inventoryItemRepository.saveAndFlush(item);
    }

    public InventoryItemEntity updateItem(int itemId, boolean isEquippedCt, boolean isEquippedT) {
        inventoryItemRepository.setEquippedCt(itemId, isEquippedCt);
        inventoryItemRepository.setEquippedT(itemId, isEquippedT);

        return inventoryItemRepository.findById(itemId).orElseThrow();
    }
}