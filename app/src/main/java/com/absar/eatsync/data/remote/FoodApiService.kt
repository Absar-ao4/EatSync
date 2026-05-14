package com.absar.eatsync.data.remote

import com.absar.eatsync.model.food.FoodAddress
import com.absar.eatsync.model.food.FoodMenuItem
import com.absar.eatsync.model.food.FoodRestaurant
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiService {

    @GET("api/food/addresses")
    suspend fun getAddresses():List<FoodAddress>

    @GET("api/food/restaurants")
    suspend fun getRestaurants():List<FoodRestaurant>

    @GET("api/food/menu/{restaurantId}")
    suspend fun getMenuItems(
        @Path("restaurantId") restaurantId:String
    ):List<FoodMenuItem>
}