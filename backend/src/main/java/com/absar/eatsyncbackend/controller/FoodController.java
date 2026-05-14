package com.absar.eatsyncbackend.controller;

import com.absar.eatsyncbackend.dto.FoodAddressDto;
import com.absar.eatsyncbackend.dto.FoodMenuItemDto;
import com.absar.eatsyncbackend.dto.FoodRestaurantDto;
import com.absar.eatsyncbackend.service.FoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/api/food/addresses")
    public List<FoodAddressDto> getAddresses() {
        return foodService.getAddresses();
    }

    @GetMapping("/api/food/restaurants")
    public List<FoodRestaurantDto> getRestaurants() {
        return foodService.getRestaurants();
    }

    @GetMapping("/api/food/menu/{restaurantId}")
    public List<FoodMenuItemDto> getMenuItems(
            @PathVariable String restaurantId
    ) {
        return foodService.getMenuItems(restaurantId);
    }
}