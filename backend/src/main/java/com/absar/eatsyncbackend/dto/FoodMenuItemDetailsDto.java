package com.absar.eatsyncbackend.dto;

import java.util.List;

public class FoodMenuItemDetailsDto {
    private String itemId;
    private String restaurantId;
    private String name;
    private int basePrice;
    private List<VariantGroupDto> variantGroups;
    private List<AddonGroupDto> addonGroups;

    public FoodMenuItemDetailsDto() {
    }

    public FoodMenuItemDetailsDto(
            String itemId,
            String restaurantId,
            String name,
            int basePrice,
            List<VariantGroupDto> variantGroups,
            List<AddonGroupDto> addonGroups
    ) {
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.basePrice = basePrice;
        this.variantGroups = variantGroups;
        this.addonGroups = addonGroups;
    }

    public String getItemId() {
        return itemId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public List<VariantGroupDto> getVariantGroups() {
        return variantGroups;
    }

    public List<AddonGroupDto> getAddonGroups() {
        return addonGroups;
    }
}