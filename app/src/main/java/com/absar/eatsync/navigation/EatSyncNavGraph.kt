package com.absar.eatsync.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.absar.eatsync.ui.screens.CreateSessionScreen
import com.absar.eatsync.ui.screens.HomeScreen
import com.absar.eatsync.ui.screens.JoinSessionScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.absar.eatsync.ui.screens.WaitingRoomScreen
import com.absar.eatsync.ui.screens.RestaurantSelectionScreen
import com.absar.eatsync.ui.screens.MenuScreen
import com.absar.eatsync.ui.screens.SharedCartScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.absar.eatsync.viewmodel.CartViewModel
import com.absar.eatsync.ui.screens.BillSplitScreen
import com.absar.eatsync.viewmodel.SessionViewModel
import com.absar.eatsync.ui.screens.CheckoutScreen

@Composable
fun EatSyncNavGraph(){
    val navController=rememberNavController()
    val cartViewModel: CartViewModel=viewModel()
    val sessionViewModel: SessionViewModel = viewModel()
    val participants by sessionViewModel.participants.collectAsState()
    val selectedRestaurant by sessionViewModel.selectedRestaurant.collectAsState()
    val sessionInfo by sessionViewModel.sessionInfo.collectAsState()
    val isCartLocked = sessionInfo?.cartLocked == true
    val cartItems by cartViewModel.cartItems.collectAsState()

    cartViewModel.updateCartLockedStatus(isCartLocked)

    NavHost(
        navController=navController,
        startDestination=Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(
                onCreateSessionClick={
                    navController.navigate(Screen.CreateSession.route)
                },
                onJoinSessionClick={
                    navController.navigate(Screen.JoinSession.route)
                }
            )
        }
        composable(Screen.CreateSession.route){
            CreateSessionScreen(
                onBackClick={
                    navController.popBackStack()
                },
                onSessionCreated={sessionCode, hostName ->
                    sessionViewModel.createFirebaseSession(
                        sessionCode=sessionCode,
                        hostName=hostName
                    )
                    cartViewModel.startCartSync(
                        sessionCode=sessionCode,
                        userName=hostName
                    )
                    navController.navigate(Screen.WaitingRoom.createRoute(sessionCode, true))
                }
            )
        }
        composable(Screen.JoinSession.route){
            JoinSessionScreen(
                onBackClick={
                    navController.popBackStack()
                },
                onSessionJoined={sessionCode, userName ->
                    sessionViewModel.joinFirebaseSession(
                        sessionCode=sessionCode,
                        userName=userName
                    )
                    cartViewModel.startCartSync(
                        sessionCode=sessionCode,
                        userName=userName
                    )
                    navController.navigate(Screen.WaitingRoom.createRoute(sessionCode, false))
                }
            )
        }
        composable(
            route=Screen.WaitingRoom.route,
            arguments=listOf(
                navArgument("sessionCode"){
                    type=NavType.StringType
                },
                navArgument("isHost"){
                    type=NavType.BoolType
                }
            )
        ){backStackEntry->
            val sessionCode=backStackEntry.arguments?.getString("sessionCode") ?: ""
            val isHost=backStackEntry.arguments?.getBoolean("isHost") ?: false
            WaitingRoomScreen(
                sessionCode=sessionCode,
                isHost=isHost,
                participants=participants,
                selectedRestaurant=selectedRestaurant,
                isCartLocked=isCartLocked,
                onSelectRestaurantClick={
                    navController.navigate(Screen.RestaurantSelection.createRoute(sessionCode))
                },
                onOpenMenuClick={restaurant->
                    navController.navigate(
                        Screen.Menu.createRoute(
                            sessionCode=sessionCode,
                            restaurantId=restaurant.id,
                            restaurantName=restaurant.name
                        )
                    )
                },
                onChangeRestaurantClick={
                    sessionViewModel.clearRestaurantAndCart()
                    navController.navigate(Screen.RestaurantSelection.createRoute(sessionCode))
                },
                onBackClick={
                    navController.popBackStack()
                }
            )
        }
        composable(
            route=Screen.RestaurantSelection.route,
            arguments=listOf(
                navArgument("sessionCode"){
                    type=NavType.StringType
                }
            )
        ){backStackEntry->
            val sessionCode=backStackEntry.arguments?.getString("sessionCode") ?: ""
            RestaurantSelectionScreen(
                sessionCode=sessionCode,
                onRestaurantSelected={restaurant->
                    if(selectedRestaurant != null && selectedRestaurant?.id?.isNotEmpty() == true){
                        navController.navigate(
                            Screen.Menu.createRoute(
                                sessionCode=sessionCode,
                                restaurantId=selectedRestaurant?.id ?: "",
                                restaurantName=selectedRestaurant?.name ?: ""
                            )
                        ){
                            popUpTo(Screen.RestaurantSelection.route){
                                inclusive=true
                            }
                        }
                        return@RestaurantSelectionScreen
                    }
                    sessionViewModel.updateSelectedRestaurant(
                        restaurantId=restaurant.id,
                        restaurantName=restaurant.name
                    )
                    navController.navigate(
                        Screen.Menu.createRoute(
                            sessionCode=sessionCode,
                            restaurantId=restaurant.id,
                            restaurantName=restaurant.name
                        )
                    ){
                        popUpTo(Screen.RestaurantSelection.route){
                            inclusive=true
                        }
                    }
                },
                onBackClick={
                    navController.popBackStack()
                }
            )
        }
        composable(
            route=Screen.Menu.route,
            arguments=listOf(
                navArgument("sessionCode"){
                    type=NavType.StringType
                },
                navArgument("restaurantId"){
                    type=NavType.StringType
                },
                navArgument("restaurantName"){
                    type=NavType.StringType
                }
            )
        ){backStackEntry->
            val sessionCode=backStackEntry.arguments?.getString("sessionCode") ?: ""
            val restaurantId=backStackEntry.arguments?.getString("restaurantId") ?: ""
            val restaurantName=backStackEntry.arguments?.getString("restaurantName") ?: ""
            MenuScreen(
                sessionCode=sessionCode,
                restaurantId=restaurantId,
                restaurantName=restaurantName,
                selectedRestaurantId=selectedRestaurant?.id,
                cartItems=cartItems,
                currentUserName=cartViewModel.getCurrentUserName(),
                isCartLocked=isCartLocked,
                onAddItemClick={item->
                    cartViewModel.addItem(item)
                },
                onIncreaseQuantity={itemId->
                    cartViewModel.increaseQuantity(itemId)
                },
                onDecreaseQuantity={itemId->
                    cartViewModel.decreaseQuantity(itemId)
                },
                onViewCartClick={
                    navController.navigate(Screen.SharedCart.createRoute(sessionCode))
                },
                onBackClick={
                    navController.popBackStack()
                }
            )
        }
        composable(
            route=Screen.SharedCart.route,
            arguments=listOf(
                navArgument("sessionCode"){
                    type=NavType.StringType
                }
            )
        ){ backStackEntry->
            val sessionCode=backStackEntry.arguments?.getString("sessionCode") ?: ""
            SharedCartScreen(
                sessionCode=sessionCode,
                cartItems=cartItems,
                currentUserName=cartViewModel.getCurrentUserName(),
                isCartLocked=isCartLocked,
                onIncreaseQuantity={itemId->
                    cartViewModel.increaseQuantity(itemId)
                },
                onDecreaseQuantity={itemId->
                    cartViewModel.decreaseQuantity(itemId)
                },
                onRemoveItem={itemId->
                    cartViewModel.removeItem(itemId)
                },
                onContinueToBillSplitClick={
                    navController.navigate(Screen.BillSplit.createRoute(sessionCode))
                },
                onBackClick={
                    navController.popBackStack()
                }
            )
        }
        composable(
            route=Screen.Checkout.route,
            arguments=listOf(
                navArgument("sessionCode") {
                    type=NavType.StringType
                }
            )
        ){backStackEntry->
            val sessionCode=backStackEntry.arguments?.getString("sessionCode") ?: ""
            CheckoutScreen(
                sessionCode=sessionCode,
                cartItems=cartItems,
                participants=participants,
                currentUserName=cartViewModel.getCurrentUserName(),
                onBackClick={
                    navController.popBackStack()
                }
            )
        }
        composable(
            route=Screen.BillSplit.route,
            arguments=listOf(
                navArgument("sessionCode"){
                    type=NavType.StringType
                }
            )
        ){backStackEntry->
            val sessionCode=backStackEntry.arguments?.getString("sessionCode") ?: ""
            BillSplitScreen(
                sessionCode=sessionCode,
                cartItems=cartItems,
                participants=participants,
                currentUserName=cartViewModel.getCurrentUserName(),
                isCartLocked=isCartLocked,
                onToggleReady={userName->
                    sessionViewModel.toggleReady(userName)
                },
                onLockCartClick={
                    sessionViewModel.lockCart()
                },
                onUnlockCartClick={
                    sessionViewModel.unlockCart()
                },
                onCheckoutClick={
                    navController.navigate(Screen.Checkout.createRoute(sessionCode))
                },
                onBackClick={
                    navController.popBackStack()
                }
            )
        }
    }
}