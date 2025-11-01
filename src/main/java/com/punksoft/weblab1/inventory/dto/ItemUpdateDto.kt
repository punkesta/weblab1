package com.punksoft.weblab1.inventory.dto

data class ItemUpdateDto(
    val id: Integer,
    val isEquippedCt: Boolean,
    val isEquippedT: Boolean
)