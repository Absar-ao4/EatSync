package com.absar.eatsync.model.food

data class FoodMenuItemDetails(
    val itemId:String = "",
    val restaurantId:String = "",
    val name:String = "",
    val basePrice:Int = 0,
    val variantGroups:List<VariantGroup> = emptyList(),
    val addonGroups:List<AddonGroup> = emptyList()
)

data class VariantGroup(
    val groupId:String = "",
    val name:String = "",
    val options:List<VariantOption> = emptyList()
)

data class VariantOption(
    val id:String = "",
    val name:String = "",
    val inStock:Boolean = true,
    val defaultSelected:Boolean = false
)

data class AddonGroup(
    val groupId:String = "",
    val groupName:String = "",
    val maxAddons:Int = 0,
    val maxFreeAddons:Int = 0,
    val choices:List<AddonChoice> = emptyList()
)

data class AddonChoice(
    val id:String = "",
    val name:String = "",
    val price:Int = 0
)