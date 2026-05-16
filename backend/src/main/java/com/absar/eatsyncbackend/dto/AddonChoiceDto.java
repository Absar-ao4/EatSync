package com.absar.eatsyncbackend.dto;

public class AddonChoiceDto {
    private String id;
    private String name;
    private int price;

    public AddonChoiceDto() {
    }

    public AddonChoiceDto(
            String id,
            String name,
            int price
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}