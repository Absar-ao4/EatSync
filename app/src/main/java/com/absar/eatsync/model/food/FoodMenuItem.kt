package com.absar.eatsync.model.food

data class FoodMenuItem(
    val id:String="",
    val restaurantId:String="",
    val name:String="",
    val description:String="",
    val price:Int=0,
    val isVeg:Boolean=true
)