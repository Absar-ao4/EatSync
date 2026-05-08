package com.absar.eatsync.model

data class CartItem(
    val id: String = "",
    val name: String = "",
    val price: Int = 0,
    val quantity: Int = 1,
    val addedByName: String = "Absar"
)