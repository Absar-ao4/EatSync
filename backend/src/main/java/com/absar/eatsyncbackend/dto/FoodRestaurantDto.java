package com.absar.eatsyncbackend.dto;

public class FoodRestaurantDto {
    private String id;
    private String name;
    private String cuisine;
    private String rating;
    private String deliveryTime;
    private String imageUrl;
    private String costForTwo;
    private String areaName;

    public FoodRestaurantDto(){
    }

    public FoodRestaurantDto(
            String id,
            String name,
            String cuisine,
            String rating,
            String deliveryTime,
            String imageUrl,
            String costForTwo,
            String areaName
    ){
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageUrl = imageUrl;
        this.costForTwo = costForTwo;
        this.areaName = areaName;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCuisine(){
        return cuisine;
    }

    public String getRating(){
        return rating;
    }

    public String getDeliveryTime(){
        return deliveryTime;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCostForTwo(){
        return costForTwo;
    }

    public String getAreaName(){
        return areaName;
    }
}