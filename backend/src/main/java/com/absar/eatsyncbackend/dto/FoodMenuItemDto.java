package com.absar.eatsyncbackend.dto;

public class FoodMenuItemDto{
    private String id;
    private String restaurantId;
    private String name;
    private String description;
    private int price;
    private boolean veg;

    public FoodMenuItemDto(){
    }

    public FoodMenuItemDto(
            String id,
            String restaurantId,
            String name,
            String description,
            int price,
            boolean veg
    ) {
        this.id=id;
        this.restaurantId=restaurantId;
        this.name=name;
        this.description=description;
        this.price=price;
        this.veg=veg;
    }

    public String getId(){
        return id;
    }

    public String getRestaurantId(){
        return restaurantId;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getPrice(){
        return price;
    }

    public boolean isVeg(){
        return veg;
    }
}