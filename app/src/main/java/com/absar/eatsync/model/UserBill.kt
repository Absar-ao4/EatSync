package com.absar.eatsync.model

data class UserBill(
    val userName:String,
    val itemTotal:Int,
    val sharedCharges:Int,
    val finalAmount:Int
)