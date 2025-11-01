package com.punksoft.weblab1.inventory.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.punksoft.weblab1.inventory.service.InventoryItemService;
import com.punksoft.weblab1.inventory.service.SellService;
import com.punksoft.weblab1.inventory.dto.InventoryItemDto;
import com.punksoft.weblab1.inventory.dto.ItemCreateDto;
import com.punksoft.weblab1.inventory.dto.ItemUpdateDto;
import com.punksoft.weblab1.inventory.mapper.InventoryItemMapper;
import com.punksoft.weblab1.security.systemuser.CustomUserDetails;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {
    private final InventoryItemService inventoryService;
    private final SellService sellService;
    private final InventoryItemMapper mapper;

    public InventoryRestController(
            InventoryItemService inventoryService, SellService sellService, InventoryItemMapper mapper
    ) {
        this.inventoryService = inventoryService;
        this.sellService = sellService;
        this.mapper = mapper;
    }

    @PostMapping
    public InventoryItemDto addItem(@RequestBody ItemCreateDto item) {
        var addedItem = inventoryService.addItem(item.getOwnerId(), item.getBaseItemId());
        return mapper.toDto(addedItem);
    }

    @GetMapping
    public List<InventoryItemDto> getInventory(@AuthenticationPrincipal CustomUserDetails user) {
        return inventoryService.getItemsByOwnerId(user.getUserId())
                               .stream()
                               .map(mapper::toDto)
                               .toList();
    }

    @GetMapping("/{id}")
    public InventoryItemDto getItem(@PathVariable("id") int id) {
        var item = inventoryService.getItemById(id);
        return mapper.toDto(item);
    }

    @PatchMapping("/{id}")
    public InventoryItemDto updateItem(@PathVariable("id") int id, @RequestBody ItemUpdateDto item) {
        var updItem = inventoryService.updateItem(id, item.isEquippedCt(), item.isEquippedT());
        return mapper.toDto(updItem);
    }

    @DeleteMapping("/{id}")
    public void sellItem(@PathVariable("id") int id) {
        sellService.sellItem(id);
    }
}