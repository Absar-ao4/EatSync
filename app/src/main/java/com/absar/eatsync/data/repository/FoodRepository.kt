package com.absar.eatsync.data.repository

import android.util.Log
import com.absar.eatsync.data.remote.RetrofitClient
import com.absar.eatsync.model.food.FoodAddress
import com.absar.eatsync.model.food.FoodMenuItem
import com.absar.eatsync.model.food.FoodRestaurant

class FoodRepository {
    suspend fun getAddresses():List<FoodAddress>{
        return try{
            val addresses=RetrofitClient.foodApiService.getAddresses()
            Log.d("EatSyncBackend", "Backend addresses loaded: ${addresses.size}")
            addresses
        }
        catch(e:Exception){
            Log.e("EatSyncBackend", "Backend addresses failed", e)
            listOf(
                FoodAddress(
                    id="dummy_college_address",
                    addressLine="College address",
                    phoneNumber="******0000",
                    addressCategory="College",
                    addressTag="College"
                )
            )
        }
    }

    suspend fun getRestaurants():List<FoodRestaurant>{
        return try{
            val restaurants=RetrofitClient.foodApiService.getRestaurants()
            Log.d("EatSyncBackend", "Backend restaurants loaded: ${restaurants.size}")
            restaurants
        }
        catch(e:Exception){
            Log.e("EatSyncBackend", "Backend restaurants failed", e)
            listOf(
                FoodRestaurant(
                    id="r1",
                    name="Pizza Hut",
                    cuisine="Pizza, Fast Food",
                    rating="4.2",
                    deliveryTime="30-35 min",
                    costForTwo="₹400 for two",
                    areaName="Patia"
                ),
                FoodRestaurant(
                    id="r2",
                    name="Burger King",
                    cuisine="Burgers, Beverages",
                    rating="4.1",
                    deliveryTime="25-30 min",
                    costForTwo="₹350 for two",
                    areaName="KIIT Square"
                ),
                FoodRestaurant(
                    id="r3",
                    name="KFC",
                    cuisine="Chicken, Fast Food",
                    rating="4.0",
                    deliveryTime="35-40 min",
                    costForTwo="₹500 for two",
                    areaName="Patia"
                ),
                FoodRestaurant(
                    id="r4",
                    name="La Pino'z Pizza",
                    cuisine="Pizza, Italian",
                    rating="4.3",
                    deliveryTime="30-35 min",
                    costForTwo="₹450 for two",
                    areaName="Infocity"
                ),
                FoodRestaurant(
                    id="r5",
                    name="Wow! Momo",
                    cuisine="Momos, Tibetan",
                    rating="4.4",
                    deliveryTime="20-25 min",
                    costForTwo="₹250 for two",
                    areaName="Patia"
                )
            )
        }
    }

    suspend fun getMenuItems(
        restaurantId:String
    ):List<FoodMenuItem>{
        return try{
            val menuItems=RetrofitClient.foodApiService.getMenuItems(restaurantId)
            Log.d("EatSyncBackend", "Backend menu loaded for $restaurantId: ${menuItems.size}")
            menuItems
        }
        catch(e:Exception){
            Log.e("EatSyncBackend", "Backend menu failed for $restaurantId", e)
            when(restaurantId){
                "r1" -> listOf(
                    FoodMenuItem(
                        id="r1_m1",
                        restaurantId="r1",
                        name="Margherita Pizza",
                        description="Classic cheese pizza with tomato sauce",
                        price=299,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r1_m2",
                        restaurantId="r1",
                        name="Farmhouse Pizza",
                        description="Capsicum, onion, tomato, corn and cheese",
                        price=399,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r1_m3",
                        restaurantId="r1",
                        name="Garlic Bread",
                        description="Toasted garlic bread with cheese dip",
                        price=159,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r1_m4",
                        restaurantId="r1",
                        name="Cheese Burst Pizza",
                        description="Extra cheesy pizza with soft crust",
                        price=449,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r1_m5",
                        restaurantId="r1",
                        name="Pepsi",
                        description="Chilled soft drink",
                        price=69,
                        veg=false,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    )
                )
                "r2" -> listOf(
                    FoodMenuItem(
                        id="r2_m1",
                        restaurantId="r2",
                        name="Veg Whopper",
                        description="Loaded veg patty with lettuce and sauces",
                        price=189,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=true
                    ),
                    FoodMenuItem(
                        id="r2_m2",
                        restaurantId="r2",
                        name="Crispy Veg Burger",
                        description="Crispy veg patty with mayo",
                        price=99,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r2_m3",
                        restaurantId="r2",
                        name="King Fries",
                        description="Crispy salted fries",
                        price=129,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=true
                    ),
                    FoodMenuItem(
                        id="r2_m4",
                        restaurantId="r2",
                        name="Cheesy Fries",
                        description="Fries loaded with cheese sauce",
                        price=169,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=true
                    ),
                    FoodMenuItem(
                        id="r2_m5",
                        restaurantId="r2",
                        name="Coke",
                        description="Chilled beverage",
                        price=79,
                        veg=false,
                        imageUrl="",
                        inStock=false,
                        hasVariants=false,
                        hasAddons=false
                    )
                )
                "r3" -> listOf(
                    FoodMenuItem(
                        id="r3_m1",
                        restaurantId="r3",
                        name="Zinger Burger",
                        description="Crispy chicken burger with spicy mayo",
                        price=219,
                        veg=false,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=true
                    ),
                    FoodMenuItem(
                        id="r3_m2",
                        restaurantId="r3",
                        name="Chicken Popcorn",
                        description="Bite-sized crispy chicken pieces",
                        price=179,
                        veg=false,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r3_m3",
                        restaurantId="r3",
                        name="Hot Wings",
                        description="Spicy fried chicken wings",
                        price=249,
                        veg=false,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r3_m4",
                        restaurantId="r3",
                        name="Chicken Bucket",
                        description="Assorted crispy chicken pieces",
                        price=599,
                        veg=false,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r3_m5",
                        restaurantId="r3",
                        name="Pepsi",
                        description="Chilled soft drink",
                        price=69,
                        veg=false,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    )
                )
                "r4" -> listOf(
                    FoodMenuItem(
                        id="r4_m1",
                        restaurantId="r4",
                        name="Cheesy 7 Pizza",
                        description="Seven cheese loaded pizza",
                        price=459,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=true
                    ),
                    FoodMenuItem(
                        id="r4_m2",
                        restaurantId="r4",
                        name="Tandoori Paneer Pizza",
                        description="Paneer tikka, onion and capsicum",
                        price=429,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=true
                    ),
                    FoodMenuItem(
                        id="r4_m3",
                        restaurantId="r4",
                        name="Garlic Sticks",
                        description="Crispy garlic sticks with herbs",
                        price=149,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r4_m4",
                        restaurantId="r4",
                        name="Pesto Veg Pizza",
                        description="Italian herbs, veggies and pesto sauce",
                        price=389,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r4_m5",
                        restaurantId="r4",
                        name="Chocolate Brownie",
                        description="Warm brownie dessert",
                        price=139,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    )
                )
                "r5" -> listOf(
                    FoodMenuItem(
                        id="r5_m1",
                        restaurantId="r5",
                        name="Veg Steamed Momos",
                        description="Classic steamed momos with spicy chutney",
                        price=129,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r5_m2",
                        restaurantId="r5",
                        name="Veg Fried Momos",
                        description="Crispy fried momos with chutney",
                        price=149,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r5_m3",
                        restaurantId="r5",
                        name="Paneer Momos",
                        description="Soft momos filled with paneer stuffing",
                        price=169,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=true,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="r5_m4",
                        restaurantId="r5",
                        name="Momo Burger",
                        description="Burger with momo-style patty",
                        price=119,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=true
                    ),
                    FoodMenuItem(
                        id="r5_m5",
                        restaurantId="r5",
                        name="Thukpa",
                        description="Hot Tibetan noodle soup",
                        price=179,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    )
                )
                else -> listOf(
                    FoodMenuItem(
                        id="default_m1",
                        restaurantId="default",
                        name="Veg Meal",
                        description="Simple meal combo",
                        price=199,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="default_m2",
                        restaurantId="default",
                        name="French Fries",
                        description="Crispy salted fries",
                        price=129,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    ),
                    FoodMenuItem(
                        id="default_m3",
                        restaurantId="default",
                        name="Cold Coffee",
                        description="Chilled coffee with ice cream",
                        price=179,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=false,
                        hasAddons=false
                    )
                )
            }
        }
    }
}