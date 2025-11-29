package com.noname.weblabs.inventory.service;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.noname.weblabs.inventory.entity.InventoryItemEntity;
import com.noname.weblabs.inventory.repo.BaseItemRepository;
import com.noname.weblabs.inventory.repo.InventoryItemRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InventoryItemServiceTest {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private BaseItemRepository baseItemRepository;

    @Autowired
    private InventoryItemService inventoryItemService;

    private final int ownerId = 102;

    private final String baseItemId = "awp_dragon_lore";

    private int itemId;

    @BeforeEach
    void setUp() {
        var baseItem = baseItemRepository.findById(baseItemId).orElseThrow();
        var item = InventoryItemEntity.builder()
                .ownerId(ownerId)
                .baseItem(baseItem)
                .build();
        itemId = inventoryItemRepository.saveAndFlush(item).getId();
    }

    @Test
    void testAddItem_success() {
        var newItem = inventoryItemService.addItem(ownerId, baseItemId);

        assertNotNull(newItem);
        assertEquals(ownerId, newItem.getOwnerId());
        assertEquals(baseItemId, newItem.getBaseItem().getId());
    }

    @Test
    void testAddItem_baseItemNotFound() {
        var invalidBaseItemId = "invalidItem123";

        assertThrows(RuntimeException.class, () -> inventoryItemService.addItem(ownerId, invalidBaseItemId));
    }

    @Test
    void testUpdateItem_success() {
        var isEquippedCt = true;
        var isEquippedT = false;

        var updatedItem = inventoryItemService.updateItem(itemId, isEquippedCt, isEquippedT);

        assertNotNull(updatedItem);
        assertEquals(isEquippedCt, updatedItem.getIsEquippedCt());
        assertEquals(isEquippedT, updatedItem.getIsEquippedCt());
    }

    @Test
    void testUpdateItem_notFound() {
        var invalidItemId = 999;

        assertThrows(RuntimeException.class, () -> inventoryItemService.updateItem(invalidItemId, true, false));
    }
}