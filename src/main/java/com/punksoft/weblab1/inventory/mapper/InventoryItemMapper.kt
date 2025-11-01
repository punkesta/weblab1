package com.punksoft.weblab1.inventory.mapper

import org.springframework.stereotype.Service
import com.punksoft.weblab1.inventory.entity.InventoryItemEntity
import com.punksoft.weblab1.inventory.dto.InventoryItemDto

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