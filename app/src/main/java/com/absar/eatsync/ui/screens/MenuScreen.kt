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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.absar.eatsync.data.repository.FoodRepository
import com.absar.eatsync.model.CartItem
import com.absar.eatsync.model.food.FoodMenuItem

@Composable
fun MenuScreen(
    sessionCode:String,
    restaurantId:String,
    restaurantName:String,
    selectedRestaurantId:String?,
    cartItems:List<CartItem>,
    currentUserName:String,
    isCartLocked:Boolean,
    onAddItemClick:(FoodMenuItem)->Unit,
    onCustomizeItemClick:(FoodMenuItem)->Unit,
    onIncreaseQuantity:(String)->Unit,
    onDecreaseQuantity:(String)->Unit,
    onViewCartClick:()->Unit,
    onBackClick:()->Unit
){
    val foodRepository=remember { FoodRepository() }
    var menuItems by remember { mutableStateOf<List<FoodMenuItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var customMessage by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(restaurantId){
        menuItems=foodRepository.getMenuItems(restaurantId)
        isLoading=false
        customMessage=null
    }
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
            text=if(isLoading){
                "Loading menu from EatSync backend..."
            }else{
                "Choose dishes from $restaurantName for your shared group order"
            },
            style=MaterialTheme.typography.bodyMedium,
            color=grayText,
            modifier=Modifier.padding(top = 6.dp, bottom = 16.dp)
        )
        customMessage?.let { message ->
            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                ),
                elevation=CardDefaults.cardElevation(
                    defaultElevation=2.dp
                ),
                shape=RoundedCornerShape(16.dp)
            ){
                Column(
                    modifier=Modifier.padding(14.dp)
                ){
                    Text(
                        text="Customization needed",
                        fontWeight=FontWeight.Bold,
                        color=orange
                    )
                    Text(
                        text=message,
                        color=grayText,
                        style=MaterialTheme.typography.bodyMedium,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
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

                val isItemDisabled=isMenuDisabled || !item.inStock
                val needsCustomization=item.hasVariants || item.hasAddons

                MenuItemCard(
                    item=item,
                    cartItem=cartItem,
                    isItemDisabled=isItemDisabled,
                    isCartLocked=isCartLocked,
                    isCurrentRestaurantActive=isCurrentRestaurantActive,
                    orange=orange,
                    darkText=darkText,
                    grayText=grayText,
                    onAddClick={
                        if(!isItemDisabled){
                            if(needsCustomization){
                                onCustomizeItemClick(item)
                            }
                            else{
                                customMessage=null
                                onAddItemClick(item)
                            }
                        }
                    },
                    onIncreaseClick={
                        if(cartItem != null && !isItemDisabled){
                            onIncreaseQuantity(cartItem.id)
                        }
                    },
                    onDecreaseClick={
                        if(cartItem != null && !isItemDisabled){
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

@Composable
fun MenuItemCard(
    item:FoodMenuItem,
    cartItem:CartItem?,
    isItemDisabled:Boolean,
    isCartLocked:Boolean,
    isCurrentRestaurantActive:Boolean,
    orange:Color,
    darkText:Color,
    grayText:Color,
    onAddClick:()->Unit,
    onIncreaseClick:()->Unit,
    onDecreaseClick:()->Unit
){
    val green=Color(0xFF48C479)
    val red=Color(0xFFE53935)
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
                .padding(14.dp),
            verticalAlignment=Alignment.CenterVertically
        ){
            if(item.imageUrl.isNotBlank()){
                AsyncImage(
                    model=item.imageUrl,
                    contentDescription=item.name,
                    modifier=Modifier
                        .size(82.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier=Modifier.width(12.dp))
            }

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

                Column(
                    modifier=Modifier.padding(top = 8.dp)
                ){
                    Text(
                        text=if(item.veg){
                            "VEG"
                        }else{
                            "NON-VEG"
                        },
                        style=MaterialTheme.typography.bodySmall,
                        fontWeight=FontWeight.Bold,
                        color=if(item.veg){
                            green
                        }else{
                            red
                        }
                    )
                    if(item.hasVariants || item.hasAddons){
                        Text(
                            text=buildString {
                                if(item.hasVariants){
                                    append("Customizable")
                                }
                                if(item.hasVariants && item.hasAddons){
                                    append(" • ")
                                }
                                if(item.hasAddons){
                                    append("Add-ons available")
                                }
                            },
                            style=MaterialTheme.typography.bodySmall,
                            fontWeight=FontWeight.Bold,
                            color=orange,
                            modifier=Modifier.padding(top = 3.dp)
                        )
                    }
                    if(!item.inStock){
                        Text(
                            text="OUT OF STOCK",
                            style=MaterialTheme.typography.bodySmall,
                            fontWeight=FontWeight.Bold,
                            color=red,
                            modifier=Modifier.padding(top = 3.dp)
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.width(10.dp))
            if(isItemDisabled){
                Text(
                    text=if(!item.inStock){
                        "OUT"
                    }else if(isCartLocked){
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
                        text=if(item.hasVariants || item.hasAddons){
                            "SELECT"
                        }else{
                            "ADD"
                        },
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