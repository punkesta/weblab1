package com.noname.weblabs.inventory.dto

data class ItemCreateDto(
    val ownerId: Int,
    val baseItemId: String
)