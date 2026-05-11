package com.absar.eatsync.data.repository

import com.absar.eatsync.model.food.FoodMenuItem
import com.absar.eatsync.model.food.FoodRestaurant

class FoodRepository {
    fun getRestaurants():List<FoodRestaurant>{
        return listOf(
            FoodRestaurant("r1", "Pizza Hut", "Pizza, Fast Food", "4.2", "30-35 min"),
            FoodRestaurant("r2", "Burger King", "Burgers, Beverages", "4.1", "25-30 min"),
            FoodRestaurant("r3", "KFC", "Chicken, Fast Food", "4.0", "35-40 min"),
            FoodRestaurant("r4", "La Pino'z Pizza", "Pizza, Italian", "4.3", "30-35 min"),
            FoodRestaurant("r5", "Wow! Momo", "Momos, Tibetan", "4.4", "20-25 min")
        )
    }
    fun getMenuItems(
        restaurantId:String
    ):List<FoodMenuItem>{
        return when(restaurantId){
            "r1" -> listOf(
                FoodMenuItem("r1_m1", "r1", "Margherita Pizza", "Classic cheese pizza with tomato sauce", 299),
                FoodMenuItem("r1_m2", "r1", "Farmhouse Pizza", "Capsicum, onion, tomato, corn and cheese", 399),
                FoodMenuItem("r1_m3", "r1", "Garlic Bread", "Toasted garlic bread with cheese dip", 159),
                FoodMenuItem("r1_m4", "r1", "Cheese Burst Pizza", "Extra cheesy pizza with soft crust", 449),
                FoodMenuItem("r1_m5", "r1", "Pepsi", "Chilled soft drink", 69, false)
            )
            "r2" -> listOf(
                FoodMenuItem("r2_m1", "r2", "Veg Whopper", "Loaded veg patty with lettuce and sauces", 189),
                FoodMenuItem("r2_m2", "r2", "Crispy Veg Burger", "Crispy veg patty with mayo", 99),
                FoodMenuItem("r2_m3", "r2", "King Fries", "Crispy salted fries", 129),
                FoodMenuItem("r2_m4", "r2", "Cheesy Fries", "Fries loaded with cheese sauce", 169),
                FoodMenuItem("r2_m5", "r2", "Coke", "Chilled beverage", 79, false)
            )
            "r3" -> listOf(
                FoodMenuItem("r3_m1", "r3", "Zinger Burger", "Crispy chicken burger with spicy mayo", 219, false),
                FoodMenuItem("r3_m2", "r3", "Chicken Popcorn", "Bite-sized crispy chicken pieces", 179, false),
                FoodMenuItem("r3_m3", "r3", "Hot Wings", "Spicy fried chicken wings", 249, false),
                FoodMenuItem("r3_m4", "r3", "Chicken Bucket", "Assorted crispy chicken pieces", 599, false),
                FoodMenuItem("r3_m5", "r3", "Pepsi", "Chilled soft drink", 69, false)
            )
            "r4" -> listOf(
                FoodMenuItem("r4_m1", "r4", "Cheesy 7 Pizza", "Seven cheese loaded pizza", 459),
                FoodMenuItem("r4_m2", "r4", "Tandoori Paneer Pizza", "Paneer tikka, onion and capsicum", 429),
                FoodMenuItem("r4_m3", "r4", "Garlic Sticks", "Crispy garlic sticks with herbs", 149),
                FoodMenuItem("r4_m4", "r4", "Pesto Veg Pizza", "Italian herbs, veggies and pesto sauce", 389),
                FoodMenuItem("r4_m5", "r4", "Chocolate Brownie", "Warm brownie dessert", 139)
            )
            "r5" -> listOf(
                FoodMenuItem("r5_m1", "r5", "Veg Steamed Momos", "Classic steamed momos with spicy chutney", 129),
                FoodMenuItem("r5_m2", "r5", "Veg Fried Momos", "Crispy fried momos with chutney", 149),
                FoodMenuItem("r5_m3", "r5", "Paneer Momos", "Soft momos filled with paneer stuffing", 169),
                FoodMenuItem("r5_m4", "r5", "Momo Burger", "Burger with momo-style patty", 119),
                FoodMenuItem("r5_m5", "r5", "Thukpa", "Hot Tibetan noodle soup", 179)
            )
            else -> listOf(
                FoodMenuItem("default_m1", "default", "Veg Meal", "Simple meal combo", 199),
                FoodMenuItem("default_m2", "default", "French Fries", "Crispy salted fries", 129),
                FoodMenuItem("default_m3", "default", "Cold Coffee", "Chilled coffee with ice cream", 179)
            )
        }
    }
}