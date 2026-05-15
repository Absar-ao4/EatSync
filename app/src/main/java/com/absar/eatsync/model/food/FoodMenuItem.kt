package com.absar.eatsync.model.food

data class FoodMenuItem(
    val id:String="",
    val restaurantId:String="",
    val name:String="",
    val description:String="",
    val price:Int=0,
    val veg:Boolean=true,
    val imageUrl:String="",
    val inStock:Boolean=true,
    val hasVariants:Boolean=false,
    val hasAddons:Boolean=false
)