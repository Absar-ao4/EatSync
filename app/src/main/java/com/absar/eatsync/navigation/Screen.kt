package com.absar.eatsync.navigation

sealed class Screen(val route: String) {
    data object Home:Screen("home")
    data object CreateSession:Screen("create_session")
    data object JoinSession:Screen("join_session")

    data object WaitingRoom:Screen("waiting_room/{sessionCode}/{isHost}"){
        fun createRoute(sessionCode:String,isHost:Boolean):String{
            return "waiting_room/$sessionCode/$isHost"
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
            return "menu/$sessionCode/$restaurantId/$restaurantName"
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