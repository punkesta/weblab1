package com.noname.weblabs.inventory.dto

data class ItemUpdateDto(
    val id: Integer,
    val isEquippedCt: Boolean,
    val isEquippedT: Boolean
)