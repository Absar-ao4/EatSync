package com.absar.eatsync.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Home:Screen("home")
    data object CreateSession:Screen("create_session")
    data object JoinSession:Screen("join_session")

    data object WaitingRoom:Screen("waiting_room/{sessionCode}/{isHost}"){
        fun createRoute(sessionCode:String,isHost:Boolean):String{
            return "waiting_room/$sessionCode/$isHost"
        }
    }

    data object AddressSelection:Screen("address_selection/{sessionCode}"){
        fun createRoute(sessionCode:String):String{
            return "address_selection/$sessionCode"
        }
    }

    data object RestaurantSelection:Screen("restaurant_selection/{sessionCode}"){
        fun createRoute(sessionCode:String):String{
            return "restaurant_selection/$sessionCode"
        }
    }

    data object Checkout : Screen("checkout/{sessionCode}"){
        fun createRoute(sessionCode: String):String{
            return "checkout/$sessionCode"
        }
    }

    data object Menu:Screen("menu/{sessionCode}/{restaurantId}/{restaurantName}"){
        fun createRoute(
            sessionCode:String,
            restaurantId:String,
            restaurantName:String
        ):String{
            return "menu/$sessionCode/$restaurantId/${Uri.encode(restaurantName)}"
        }
    }

    data object Customization:Screen("customization/{sessionCode}/{restaurantId}/{itemId}/{itemName}"){
        fun createRoute(
            sessionCode:String,
            restaurantId:String,
            itemId:String,
            itemName:String
        ):String{
            return "customization/$sessionCode/$restaurantId/$itemId/${Uri.encode(itemName)}"
        }
    }

    data object SharedCart:Screen("shared_cart/{sessionCode}"){
        fun createRoute(sessionCode:String):String{
            return "shared_cart/$sessionCode"
        }
    }

    data object BillSplit:Screen("bill_split/{sessionCode}"){
        fun createRoute(sessionCode:String):String{
            return "bill_split/$sessionCode"
        }
    }
}