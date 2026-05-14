package com.absar.eatsyncbackend.service;

import com.absar.eatsyncbackend.dto.FoodAddressDto;
import com.absar.eatsyncbackend.dto.FoodMenuItemDto;
import com.absar.eatsyncbackend.dto.FoodRestaurantDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    public List<FoodAddressDto> getAddresses() {
        return List.of(
                new FoodAddressDto(
                        "dummy_college_address",
                        "College address",
                        "******0000",
                        "College",
                        "College"
                )
        );
    }

    public List<FoodRestaurantDto> getRestaurants() {
        return List.of(
                new FoodRestaurantDto(
                        "r1",
                        "Pizza Hut",
                        "Pizza, Fast Food",
                        "4.2",
                        "30-35 min",
                        "",
                        "₹400 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "r2",
                        "Burger King backend",
                        "Burgers, Beverages",
                        "4.1",
                        "25-30 min",
                        "",
                        "₹350 for two",
                        "KIIT Square"
                ),
                new FoodRestaurantDto(
                        "r3",
                        "KFC",
                        "Chicken, Fast Food",
                        "4.0",
                        "35-40 min",
                        "",
                        "₹500 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "r4",
                        "La Pino'z Pizza",
                        "Pizza, Italian",
                        "4.3",
                        "30-35 min",
                        "",
                        "₹450 for two",
                        "Infocity"
                ),
                new FoodRestaurantDto(
                        "r5",
                        "Wow! Momo",
                        "Momos, Tibetan",
                        "4.4",
                        "20-25 min",
                        "",
                        "₹250 for two",
                        "Patia"
                )
        );
    }

    public List<FoodMenuItemDto> getMenuItems(String restaurantId) {
        return switch (restaurantId) {
            case "r1" -> List.of(
                    new FoodMenuItemDto("r1_m1", "r1", "Margherita Pizza", "Classic cheese pizza with tomato sauce", 299, true),
                    new FoodMenuItemDto("r1_m2", "r1", "Farmhouse Pizza", "Capsicum, onion, tomato, corn and cheese", 399, true),
                    new FoodMenuItemDto("r1_m3", "r1", "Garlic Bread", "Toasted garlic bread with cheese dip", 159, true),
                    new FoodMenuItemDto("r1_m4", "r1", "Cheese Burst Pizza", "Extra cheesy pizza with soft crust", 449, true),
                    new FoodMenuItemDto("r1_m5", "r1", "Pepsi", "Chilled soft drink", 69, false)
            );
            case "r2" -> List.of(
                    new FoodMenuItemDto("r2_m1", "r2", "Veg Whopper", "Loaded veg patty with lettuce and sauces", 189, true),
                    new FoodMenuItemDto("r2_m2", "r2", "Crispy Veg Burger", "Crispy veg patty with mayo", 99, true),
                    new FoodMenuItemDto("r2_m3", "r2", "King Fries", "Crispy salted fries", 129, true),
                    new FoodMenuItemDto("r2_m4", "r2", "Cheesy Fries", "Fries loaded with cheese sauce", 169, true),
                    new FoodMenuItemDto("r2_m5", "r2", "Coke", "Chilled beverage", 79, false)
            );
            case "r3" -> List.of(
                    new FoodMenuItemDto("r3_m1", "r3", "Zinger Burger", "Crispy chicken burger with spicy mayo", 219, false),
                    new FoodMenuItemDto("r3_m2", "r3", "Chicken Popcorn", "Bite-sized crispy chicken pieces", 179, false),
                    new FoodMenuItemDto("r3_m3", "r3", "Hot Wings", "Spicy fried chicken wings", 249, false),
                    new FoodMenuItemDto("r3_m4", "r3", "Chicken Bucket", "Assorted crispy chicken pieces", 599, false),
                    new FoodMenuItemDto("r3_m5", "r3", "Pepsi", "Chilled soft drink", 69, false)
            );
            case "r4" -> List.of(
                    new FoodMenuItemDto("r4_m1", "r4", "Cheesy 7 Pizza", "Seven cheese loaded pizza", 459, true),
                    new FoodMenuItemDto("r4_m2", "r4", "Tandoori Paneer Pizza", "Paneer tikka, onion and capsicum", 429, true),
                    new FoodMenuItemDto("r4_m3", "r4", "Garlic Sticks", "Crispy garlic sticks with herbs", 149, true),
                    new FoodMenuItemDto("r4_m4", "r4", "Pesto Veg Pizza", "Italian herbs, veggies and pesto sauce", 389, true),
                    new FoodMenuItemDto("r4_m5", "r4", "Chocolate Brownie", "Warm brownie dessert", 139, true)
            );
            case "r5" -> List.of(
                    new FoodMenuItemDto("r5_m1", "r5", "Veg Steamed Momos", "Classic steamed momos with spicy chutney", 129, true),
                    new FoodMenuItemDto("r5_m2", "r5", "Veg Fried Momos", "Crispy fried momos with chutney", 149, true),
                    new FoodMenuItemDto("r5_m3", "r5", "Paneer Momos", "Soft momos filled with paneer stuffing", 169, true),
                    new FoodMenuItemDto("r5_m4", "r5", "Momo Burger", "Burger with momo-style patty", 119, true),
                    new FoodMenuItemDto("r5_m5", "r5", "Thukpa", "Hot Tibetan noodle soup", 179, true)
            );
            default -> List.of(
                    new FoodMenuItemDto("default_m1", "default", "Veg Meal", "Simple meal combo", 199, true),
                    new FoodMenuItemDto("default_m2", "default", "French Fries", "Crispy salted fries", 129, true),
                    new FoodMenuItemDto("default_m3", "default", "Cold Coffee", "Chilled coffee with ice cream", 179, true)
            );
        };
    }
}