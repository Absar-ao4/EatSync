package com.absar.eatsyncbackend.dto;

public class FoodMenuItemDto {
    private String id;
    private String restaurantId;
    private String name;
    private String description;
    private int price;
    private boolean veg;
    private String imageUrl;
    private boolean inStock;
    private boolean hasVariants;
    private boolean hasAddons;

    public FoodMenuItemDto() {
    }

    public FoodMenuItemDto(
            String id,
            String restaurantId,
            String name,
            String description,
            int price,
            boolean veg,
            String imageUrl,
            boolean inStock,
            boolean hasVariants,
            boolean hasAddons
    ) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.veg = veg;
        this.imageUrl = imageUrl;
        this.inStock = inStock;
        this.hasVariants = hasVariants;
        this.hasAddons = hasAddons;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean isVeg() {
        return veg;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isInStock() {
        return inStock;
    }

    public boolean isHasVariants() {
        return hasVariants;
    }

    public boolean isHasAddons() {
        return hasAddons;
    }
}