package com.noname.weblabs.inventory.mapper

import org.springframework.stereotype.Service
import com.noname.weblabs.inventory.entity.InventoryItemEntity
import com.noname.weblabs.inventory.dto.InventoryItemDto

@Service
class InventoryItemMapper {
    fun toDto(entity: InventoryItemEntity): InventoryItemDto {
        return InventoryItemDto(
            entity.id,
            entity.ownerId,
            entity.baseItem.id,
            entity.isNew,
            entity.isStatTrack,
            entity.isEquippedT,
            entity.isEquippedCt
        )
    }
}