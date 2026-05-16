package com.absar.eatsyncbackend.dto;

public class VariantOptionDto {
    private String id;
    private String name;
    private boolean inStock;
    private boolean defaultSelected;

    public VariantOptionDto() {
    }

    public VariantOptionDto(
            String id,
            String name,
            boolean inStock,
            boolean defaultSelected
    ) {
        this.id = id;
        this.name = name;
        this.inStock = inStock;
        this.defaultSelected = defaultSelected;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isInStock() {
        return inStock;
    }

    public boolean isDefaultSelected() {
        return defaultSelected;
    }
}