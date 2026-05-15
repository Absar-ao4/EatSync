package com.absar.eatsyncbackend.client;

import org.springframework.stereotype.Component;

@Component
public class SwiggyMcpClient {

    public void callTool(
            String toolName,
            String arguments
    ){
        System.out.println("Swiggy MCP tool requested: " + toolName);
        System.out.println("Arguments: " + arguments);
    }
    public void getAddresses() {
        callTool(
                "get_addresses",
                "{}"
        );
    }
    public void searchRestaurants(
            String addressId,
            String query
    ){
        callTool(
                "search_restaurants",
                "{ addressId: \"" + addressId + "\", query: \"" + query + "\" }"
        );
    }
    public void getRestaurantMenu(
            String addressId,
            String restaurantId,
            int page,
            int pageSize
    ){
        callTool(
                "get_restaurant_menu",
                "{ addressId: \"" + addressId + "\", restaurantId: \"" + restaurantId + "\", page: " + page + ", pageSize: " + pageSize + " }"
        );
    }
    public void searchMenu(
            String addressId,
            String restaurantId,
            String query
    ){
        callTool(
                "search_menu",
                "{ addressId: \"" + addressId + "\", restaurantIdOfAddedItem: \"" + restaurantId + "\", query: \"" + query + "\" }"
        );
    }
}