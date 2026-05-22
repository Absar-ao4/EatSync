package com.absar.eatsync.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

    LaunchedEffect(restaurantId){
        isLoading=true
        menuItems=foodRepository.getMenuItems(restaurantId)
        isLoading=false
    }

    val orange=Color(0xFFFC8019)
    val deepOrange=Color(0xFFE95D00)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val lightOrange=Color(0xFFFFE8D2)
    val green=Color(0xFF48C479)

    val isCurrentRestaurantActive=selectedRestaurantId==restaurantId
    val isMenuDisabled=isCartLocked || !isCurrentRestaurantActive

    val userCartCount=cartItems
        .filter { it.addedByName==currentUserName }
        .sumOf { it.quantity }

    Box(
        modifier=Modifier
            .fillMaxSize()
            .background(background)
    ){
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(
                    Brush.verticalGradient(
                        colors=listOf(
                            Color(0xFFFFD2A1),
                            background
                        )
                    )
                )
        )
        Box(
            modifier=Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 18.dp)
                .background(Color(0x33FFFFFF), CircleShape)
                .size(130.dp)
        )
        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ){
            Spacer(modifier=Modifier.height(28.dp))
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.Top
            ){
                Column(
                    modifier=Modifier.weight(1f)
                ){
                    Text(
                        text=restaurantName,
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text=if(isLoading){
                            "Fetching menu from EatSync backend..."
                        }else{
                            "${menuItems.size} items available for group order"
                        },
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
                Surface(
                    shape=RoundedCornerShape(18.dp),
                    color=Color.White,
                    shadowElevation=3.dp
                ){
                    Column(
                        modifier=Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        Text(
                            text="SESSION",
                            style=MaterialTheme.typography.labelSmall,
                            color=grayText,
                            fontWeight=FontWeight.Bold
                        )
                        Text(
                            text=sessionCode,
                            color=orange,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.height(18.dp))
            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation=8.dp,
                        shape=RoundedCornerShape(26.dp)
                    ),
                shape=RoundedCornerShape(26.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ){
                Column(
                    modifier=Modifier.padding(18.dp)
                ){
                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement=Arrangement.SpaceBetween,
                        verticalAlignment=Alignment.CenterVertically
                    ){
                        Column(
                            modifier=Modifier.weight(1f)
                        ){
                            Text(
                                text="Group menu",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )
                            Text(
                                text="Add simple items directly. Customize items with variants or add-ons before adding.",
                                style=MaterialTheme.typography.bodySmall,
                                color=grayText,
                                modifier=Modifier.padding(top = 4.dp)
                            )
                        }
                        Box(
                            modifier=Modifier
                                .background(lightOrange, RoundedCornerShape(50.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ){
                            Text(
                                text="Railway API",
                                color=deepOrange,
                                style=MaterialTheme.typography.bodySmall,
                                fontWeight=FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier=Modifier.height(14.dp))
                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement=Arrangement.spacedBy(10.dp)
                    ){
                        MenuInfoChip(
                            text=if(isCartLocked) "Cart locked" else "Cart open",
                            color=if(isCartLocked) orange else green
                        )
                        MenuInfoChip(
                            text="$userCartCount in your cart",
                            color=orange
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.height(14.dp))
            if(!isCurrentRestaurantActive){
                StatusCard(
                    title="Restaurant changed",
                    message="The host changed the restaurant. This old menu is no longer active. Go back and open the latest menu.",
                    accentColor=orange,
                    darkText=darkText,
                    grayText=grayText
                )
                Spacer(modifier=Modifier.height(14.dp))
            }
            else if(isCartLocked){
                StatusCard(
                    title="Cart is locked",
                    message="Menu items can no longer be added because the host has locked checkout.",
                    accentColor=orange,
                    darkText=darkText,
                    grayText=grayText
                )
                Spacer(modifier=Modifier.height(14.dp))
            }
            if(isLoading){
                Card(
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(24.dp),
                    colors=CardDefaults.cardColors(
                        containerColor=Color.White
                    )
                ){
                    Column(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        CircularProgressIndicator(
                            color=orange
                        )
                        Text(
                            text="Loading menu items...",
                            color=grayText,
                            modifier=Modifier.padding(top = 12.dp)
                        )
                    }
                }
                Spacer(modifier=Modifier.weight(1f))
            }
            else{
                LazyColumn(
                    modifier=Modifier.weight(1f)
                ){
                    items(menuItems){item->
                        val exactItemId="${item.id}_$currentUserName"
                        val needsCustomization=item.hasVariants || item.hasAddons
                        val customizedItemCount=cartItems
                            .filter { cartItem ->
                                cartItem.addedByName==currentUserName &&
                                        (
                                                cartItem.id==exactItemId ||
                                                        cartItem.id.startsWith("${item.id}_custom_")
                                                )
                            }
                            .sumOf { cartItem ->
                                cartItem.quantity
                            }
                        val cartItem=if(needsCustomization){
                            null
                        }else{
                            cartItems.firstOrNull{
                                it.id==exactItemId&&it.addedByName==currentUserName
                            }
                        }
                        val isItemDisabled=isMenuDisabled || !item.inStock
                        MenuItemCard(
                            item=item,
                            cartItem=cartItem,
                            customizedItemCount=customizedItemCount,
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
                                    }else{
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
                        Spacer(modifier=Modifier.height(14.dp))
                    }
                }
            }
            Spacer(modifier=Modifier.height(10.dp))
            Button(
                onClick=onViewCartClick,
                modifier=Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors=ButtonDefaults.buttonColors(
                    containerColor=orange
                ),
                shape=RoundedCornerShape(16.dp)
            ){
                Text(
                    text=if(userCartCount>0){
                        "View Shared Cart • $userCartCount item(s)"
                    }else{
                        "View Shared Cart"
                    },
                    fontWeight=FontWeight.Bold
                )
            }
            Spacer(modifier=Modifier.height(10.dp))
            OutlinedButton(
                onClick=onBackClick,
                modifier=Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape=RoundedCornerShape(16.dp),
                colors=ButtonDefaults.outlinedButtonColors(
                    containerColor=Color.White
                )
            ){
                Text(
                    text=if(!isCurrentRestaurantActive){
                        "Back to latest session"
                    }else{
                        "Back"
                    },
                    color=orange,
                    fontWeight=FontWeight.Bold
                )
            }
            Spacer(modifier=Modifier.height(14.dp))
        }
    }
}

@Composable
fun MenuInfoChip(
    text:String,
    color:Color
){
    Box(
        modifier=Modifier
            .background(
                color=color.copy(alpha = 0.12f),
                shape=RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ){
        Text(
            text=text,
            color=color,
            style=MaterialTheme.typography.bodySmall,
            fontWeight=FontWeight.Bold
        )
    }
}

@Composable
fun StatusCard(
    title:String,
    message:String,
    accentColor:Color,
    darkText:Color,
    grayText:Color
){
    Card(
        modifier=Modifier.fillMaxWidth(),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=3.dp
        ),
        shape=RoundedCornerShape(20.dp)
    ){
        Column(
            modifier=Modifier.padding(16.dp)
        ){
            Text(
                text=title,
                fontWeight=FontWeight.Bold,
                color=accentColor
            )
            Text(
                text=message,
                color=grayText,
                modifier=Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Composable
fun MenuItemCard(
    item:FoodMenuItem,
    cartItem:CartItem?,
    customizedItemCount:Int,
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
    val softOrange=Color(0xFFFFF1E3)
    val chipBg=Color(0xFFF8F8F8)

    Card(
        modifier=Modifier.fillMaxWidth(),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=5.dp
        ),
        shape=RoundedCornerShape(26.dp)
    ){
        Column(
            modifier=Modifier.fillMaxWidth()
        ){
            Box(
                modifier=Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(softOrange)
            ){
                if(item.imageUrl.isNotBlank()){
                    AsyncImage(
                        model=item.imageUrl,
                        contentDescription=item.name,
                        contentScale=ContentScale.Crop,
                        modifier=Modifier.fillMaxSize()
                    )
                }else{
                    Box(
                        modifier=Modifier.fillMaxSize(),
                        contentAlignment=Alignment.Center
                    ){
                        Text(
                            text=item.name.take(1),
                            style=MaterialTheme.typography.displaySmall,
                            fontWeight=FontWeight.ExtraBold,
                            color=orange
                        )
                    }
                }
                Box(
                    modifier=Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            Brush.verticalGradient(
                                colors=listOf(
                                    Color.Black.copy(alpha = 0.28f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                Row(
                    modifier=Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                    horizontalArrangement=Arrangement.spacedBy(6.dp)
                ){
                    VegChip(
                        isVeg=item.veg,
                        green=green,
                        red=red
                    )
                    if(!item.inStock){
                        MiniTag(
                            text="Out of stock",
                            color=red
                        )
                    }
                }
                if(item.hasVariants || item.hasAddons){
                    Box(
                        modifier=Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                    ){
                        MiniTag(
                            text=if(customizedItemCount>0){
                                "Added $customizedItemCount"
                            }else{
                                "Customizable"
                            },
                            color=orange
                        )
                    }
                }
            }
            Column(
                modifier=Modifier.padding(16.dp)
            ){
                Text(
                    text=item.name,
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.ExtraBold,
                    color=darkText
                )
                if(item.description.isNotBlank()){
                    Text(
                        text=item.description,
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 6.dp),
                        maxLines=2
                    )
                }
                Spacer(modifier=Modifier.height(10.dp))
                Row(
                    horizontalArrangement=Arrangement.spacedBy(8.dp)
                ){
                    if(item.hasVariants){
                        MiniTag(
                            text="Variants",
                            color=orange
                        )
                    }
                    if(item.hasAddons){
                        MiniTag(
                            text="Add-ons",
                            color=orange
                        )
                    }
                    if(item.inStock){
                        Box(
                            modifier=Modifier
                                .background(
                                    color=green.copy(alpha = 0.10f),
                                    shape=RoundedCornerShape(50.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ){
                            Text(
                                text="In stock",
                                color=green,
                                style=MaterialTheme.typography.labelSmall,
                                fontWeight=FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier=Modifier.height(14.dp))
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Column{
                        Text(
                            text="Price",
                            style=MaterialTheme.typography.bodySmall,
                            color=grayText
                        )
                        Text(
                            text="₹${item.price}",
                            style=MaterialTheme.typography.titleLarge,
                            fontWeight=FontWeight.ExtraBold,
                            color=darkText
                        )
                    }
                    when{
                        isItemDisabled -> {
                            Box(
                                modifier=Modifier
                                    .background(
                                        color=chipBg,
                                        shape=RoundedCornerShape(14.dp)
                                    )
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                            ){
                                Text(
                                    text=if(!item.inStock){
                                        "OUT OF STOCK"
                                    }else if(isCartLocked){
                                        "LOCKED"
                                    }else if(!isCurrentRestaurantActive){
                                        "OLD MENU"
                                    }else{
                                        "DISABLED"
                                    },
                                    color=grayText,
                                    fontWeight=FontWeight.Bold,
                                    style=MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        cartItem == null -> {
                            OutlinedButton(
                                onClick=onAddClick,
                                shape=RoundedCornerShape(16.dp),
                                colors=ButtonDefaults.outlinedButtonColors(
                                    containerColor=if(customizedItemCount>0){
                                        Color(0xFFFFF1E3)
                                    }else{
                                        Color.White
                                    }
                                )
                            ){
                                Text(
                                    text=if(item.hasVariants || item.hasAddons){
                                        if(customizedItemCount>0){
                                            "SELECT +$customizedItemCount"
                                        }else{
                                            "SELECT"
                                        }
                                    }else{
                                        "ADD"
                                    },
                                    color=orange,
                                    fontWeight=FontWeight.ExtraBold
                                )
                            }
                        }

                        else -> {
                            Row(
                                verticalAlignment=Alignment.CenterVertically,
                                horizontalArrangement=Arrangement.spacedBy(8.dp)
                            ){
                                OutlinedButton(
                                    onClick=onDecreaseClick,
                                    shape=RoundedCornerShape(14.dp)
                                ){
                                    Text(
                                        text="-",
                                        color=orange,
                                        fontWeight=FontWeight.Bold
                                    )
                                }
                                Text(
                                    text="${cartItem.quantity}",
                                    fontWeight=FontWeight.ExtraBold,
                                    color=darkText
                                )
                                OutlinedButton(
                                    onClick=onIncreaseClick,
                                    shape=RoundedCornerShape(14.dp)
                                ){
                                    Text(
                                        text="+",
                                        color=orange,
                                        fontWeight=FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VegChip(
    isVeg: Boolean,
    green: Color,
    red: Color
) {
    val chipColor = if (isVeg) green else red

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(50.dp),
                clip = false
            )
            .background(
                color = Color.White.copy(alpha = 0.96f),
                shape = RoundedCornerShape(50.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.7f),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(chipColor, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = if (isVeg) "VEG" else "NON-VEG",
            color = chipColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun MiniTag(
    text: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(50.dp),
                clip = false
            )
            .background(
                color = Color.White.copy(alpha = 0.96f),
                shape = RoundedCornerShape(50.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.75f),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold
        )
    }
}