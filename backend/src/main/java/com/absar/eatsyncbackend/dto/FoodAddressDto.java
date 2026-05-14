package com.absar.eatsyncbackend.dto;

public class FoodAddressDto{
    private String id;
    private String addressLine;
    private String phoneNumber;
    private String addressCategory;
    private String addressTag;

    public FoodAddressDto(){
    }

    public FoodAddressDto(
            String id,
            String addressLine,
            String phoneNumber,
            String addressCategory,
            String addressTag
    ){
        this.id=id;
        this.addressLine=addressLine;
        this.phoneNumber=phoneNumber;
        this.addressCategory=addressCategory;
        this.addressTag=addressTag;
    }

    public String getId(){
        return id;
    }

    public String getAddressLine(){
        return addressLine;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getAddressCategory(){
        return addressCategory;
    }

    public String getAddressTag(){
        return addressTag;
    }
}