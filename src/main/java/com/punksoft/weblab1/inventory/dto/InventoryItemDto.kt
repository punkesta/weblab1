package com.punksoft.weblab1.inventory.dto

data class InventoryItemDto(
    val id: Int,
    val ownerId: Int,
    val baseItemId: String,
    val isNew: Boolean,
    val isStatTrack: Boolean,
    val isEquippedCt: Boolean,
    val isEquippedT: Boolean
)