package com.absar.eatsync.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.model.CartItem

data class DummyMenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Int
)

@Composable
fun MenuScreen(
    sessionCode:String,
    restaurantId:String,
    restaurantName:String,
    selectedRestaurantId:String?,
    cartItems:List<CartItem>,
    currentUserName:String,
    isCartLocked:Boolean,
    onAddItemClick:(DummyMenuItem)->Unit,
    onIncreaseQuantity:(String)->Unit,
    onDecreaseQuantity:(String)->Unit,
    onViewCartClick:()->Unit,
    onBackClick:()->Unit
){
    val menuItems=getDummyMenuForRestaurant(restaurantId)
    val orange=Color(0xFFFC8019)
    val background=Color(0xFFFFF8F1)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val isCurrentRestaurantActive=selectedRestaurantId==restaurantId
    val isMenuDisabled=isCartLocked || !isCurrentRestaurantActive

    Column(
        modifier=Modifier
            .fillMaxSize()
            .background(background)
            .padding(20.dp)
    ){
        Text(
            text=restaurantName,
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold,
            color=darkText
        )
        Text(
            text="Session: $sessionCode",
            style=MaterialTheme.typography.bodyMedium,
            color=grayText,
            modifier=Modifier.padding(top = 4.dp)
        )
        Text(
            text="Choose dishes from $restaurantName for your shared group order",
            style=MaterialTheme.typography.bodyMedium,
            color=grayText,
            modifier=Modifier.padding(top = 6.dp, bottom = 16.dp)
        )
        if(!isCurrentRestaurantActive){
            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                ),
                shape=RoundedCornerShape(18.dp)
            ){
                Column(
                    modifier=Modifier.padding(16.dp)
                ){
                    Text(
                        text="Restaurant changed",
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )

                    Text(
                        text="The host changed the restaurant. This old menu is no longer active. Go back and open the latest menu.",
                        color=grayText,
                        modifier=Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
        else if(isCartLocked){
            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                ),
                shape=RoundedCornerShape(18.dp)
            ){
                Text(
                    text="Cart is locked. Menu items can no longer be added.",
                    modifier=Modifier.padding(16.dp),
                    fontWeight=FontWeight.Bold,
                    color=darkText
                )
            }
        }
        LazyColumn(
            modifier=Modifier.weight(1f)
        ){
            items(menuItems){item->
                val itemId="${item.id}_$currentUserName"
                val cartItem=cartItems.firstOrNull{
                    it.id==itemId&&it.addedByName==currentUserName
                }
                MenuItemCard(
                    item=item,
                    cartItem=cartItem,
                    isMenuDisabled=isMenuDisabled,
                    isCartLocked=isCartLocked,
                    isCurrentRestaurantActive=isCurrentRestaurantActive,
                    orange=orange,
                    darkText=darkText,
                    grayText=grayText,
                    onAddClick={
                        if(!isMenuDisabled){
                            onAddItemClick(item)
                        }
                    },
                    onIncreaseClick={
                        if(cartItem != null && !isMenuDisabled){
                            onIncreaseQuantity(cartItem.id)
                        }
                    },
                    onDecreaseClick={
                        if(cartItem != null && !isMenuDisabled){
                            onDecreaseQuantity(cartItem.id)
                        }
                    }
                )
                Spacer(modifier=Modifier.height(12.dp))
            }
        }
        Button(
            onClick=onViewCartClick,
            modifier=Modifier.fillMaxWidth(),
            colors=ButtonDefaults.buttonColors(
                containerColor=orange
            ),
            shape=RoundedCornerShape(14.dp)
        ){
            Text("View Shared Cart")
        }
        Spacer(modifier=Modifier.height(12.dp))
        OutlinedButton(
            onClick=onBackClick,
            modifier=Modifier.fillMaxWidth(),
            shape=RoundedCornerShape(14.dp)
        ){
            Text(
                text=if(!isCurrentRestaurantActive){
                    "Back to latest session"
                }else{
                    "Back"
                },
                color=orange
            )
        }
    }
}

private fun getDummyMenuForRestaurant(
    restaurantId:String
):List<DummyMenuItem>{
    return when(restaurantId){
        "r1" -> listOf(
            DummyMenuItem("r1_m1", "Margherita Pizza", "Classic cheese pizza with tomato sauce", 299),
            DummyMenuItem("r1_m2", "Farmhouse Pizza", "Capsicum, onion, tomato, corn and cheese", 399),
            DummyMenuItem("r1_m3", "Garlic Bread", "Toasted garlic bread with cheese dip", 159),
            DummyMenuItem("r1_m4", "Cheese Burst Pizza", "Extra cheesy pizza with soft crust", 449),
            DummyMenuItem("r1_m5", "Pepsi", "Chilled soft drink", 69)
        )
        "r2" -> listOf(
            DummyMenuItem("r2_m1", "Veg Whopper", "Loaded veg patty with lettuce and sauces", 189),
            DummyMenuItem("r2_m2", "Crispy Veg Burger", "Crispy veg patty with mayo", 99),
            DummyMenuItem("r2_m3", "King Fries", "Crispy salted fries", 129),
            DummyMenuItem("r2_m4", "Cheesy Fries", "Fries loaded with cheese sauce", 169),
            DummyMenuItem("r2_m5", "Coke", "Chilled beverage", 79)
        )
        "r3" -> listOf(
            DummyMenuItem("r3_m1", "Zinger Burger", "Crispy chicken burger with spicy mayo", 219),
            DummyMenuItem("r3_m2", "Chicken Popcorn", "Bite-sized crispy chicken pieces", 179),
            DummyMenuItem("r3_m3", "Hot Wings", "Spicy fried chicken wings", 249),
            DummyMenuItem("r3_m4", "Chicken Bucket", "Assorted crispy chicken pieces", 599),
            DummyMenuItem("r3_m5", "Pepsi", "Chilled soft drink", 69)
        )
        "r4" -> listOf(
            DummyMenuItem("r4_m1", "Cheesy 7 Pizza", "Seven cheese loaded pizza", 459),
            DummyMenuItem("r4_m2", "Tandoori Paneer Pizza", "Paneer tikka, onion and capsicum", 429),
            DummyMenuItem("r4_m3", "Garlic Sticks", "Crispy garlic sticks with herbs", 149),
            DummyMenuItem("r4_m4", "Pesto Veg Pizza", "Italian herbs, veggies and pesto sauce", 389),
            DummyMenuItem("r4_m5", "Chocolate Brownie", "Warm brownie dessert", 139)
        )
        "r5" -> listOf(
            DummyMenuItem("r5_m1", "Veg Steamed Momos", "Classic steamed momos with spicy chutney", 129),
            DummyMenuItem("r5_m2", "Veg Fried Momos", "Crispy fried momos with chutney", 149),
            DummyMenuItem("r5_m3", "Paneer Momos", "Soft momos filled with paneer stuffing", 169),
            DummyMenuItem("r5_m4", "Momo Burger", "Burger with momo-style patty", 119),
            DummyMenuItem("r5_m5", "Thukpa", "Hot Tibetan noodle soup", 179)
        )
        else -> listOf(
            DummyMenuItem("default_m1", "Veg Meal", "Simple meal combo", 199),
            DummyMenuItem("default_m2", "French Fries", "Crispy salted fries", 129),
            DummyMenuItem("default_m3", "Cold Coffee", "Chilled coffee with ice cream", 179)
        )
    }
}

@Composable
fun MenuItemCard(
    item:DummyMenuItem,
    cartItem:CartItem?,
    isMenuDisabled:Boolean,
    isCartLocked:Boolean,
    isCurrentRestaurantActive:Boolean,
    orange:Color,
    darkText:Color,
    grayText:Color,
    onAddClick:()->Unit,
    onIncreaseClick:()->Unit,
    onDecreaseClick:()->Unit
){
    Card(
        modifier=Modifier.fillMaxWidth(),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=3.dp
        ),
        shape=RoundedCornerShape(18.dp)
    ){
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment=Alignment.CenterVertically
        ){
            Column(
                modifier=Modifier.weight(1f)
            ){
                Text(
                    text=item.name,
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.Bold,
                    color=darkText
                )
                Text(
                    text=item.description,
                    style=MaterialTheme.typography.bodyMedium,
                    color=grayText,
                    modifier=Modifier.padding(top = 4.dp)
                )
                Text(
                    text="₹${item.price}",
                    style=MaterialTheme.typography.titleSmall,
                    fontWeight=FontWeight.Bold,
                    color=darkText,
                    modifier=Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier=Modifier.width(12.dp))
            if(isMenuDisabled){
                Text(
                    text=if(isCartLocked){
                        "LOCKED"
                    }else if(!isCurrentRestaurantActive){
                        "OLD MENU"
                    }else{
                        "DISABLED"
                    },
                    color=grayText,
                    fontWeight=FontWeight.Bold
                )
            }
            else if(cartItem == null){
                OutlinedButton(
                    onClick=onAddClick,
                    shape=RoundedCornerShape(12.dp)
                ){
                    Text(
                        text="ADD",
                        fontWeight=FontWeight.Bold,
                        color=orange
                    )
                }
            }
            else{
                Row(
                    verticalAlignment=Alignment.CenterVertically,
                    horizontalArrangement=Arrangement.Center
                ){
                    OutlinedButton(
                        onClick=onDecreaseClick,
                        shape=RoundedCornerShape(12.dp)
                    ){
                        Text(
                            text="-",
                            color=orange
                        )
                    }
                    Text(
                        text="${cartItem.quantity}",
                        modifier=Modifier.padding(horizontal = 10.dp),
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )
                    OutlinedButton(
                        onClick=onIncreaseClick,
                        shape=RoundedCornerShape(12.dp)
                    ){
                        Text(
                            text="+",
                            color=orange
                        )
                    }
                }
            }
        }
    }
}