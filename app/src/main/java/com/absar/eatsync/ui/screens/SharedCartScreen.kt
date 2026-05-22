package com.absar.eatsync.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.model.CartItem

@Composable
fun SharedCartScreen(
    sessionCode:String,
    cartItems:List<CartItem>,
    currentUserName:String,
    isCartLocked:Boolean,
    onIncreaseQuantity:(String) -> Unit,
    onDecreaseQuantity:(String) -> Unit,
    onRemoveItem:(String)->Unit,
    onContinueToBillSplitClick:()->Unit,
    onBackClick:()->Unit
) {
    val itemTotal=cartItems.sumOf{it.price * it.quantity}
    val deliveryFee=if(cartItems.isEmpty()) 0 else 40
    val platformFee=if(cartItems.isEmpty()) 0 else 10
    val grandTotal=itemTotal+deliveryFee+platformFee

    val orange=Color(0xFFFC8019)
    val deepOrange=Color(0xFFE95D00)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)
    val softOrange=Color(0xFFFFE8D2)

    val totalQuantity=cartItems.sumOf { it.quantity }

    Box(
        modifier=Modifier
            .fillMaxSize()
            .background(background)
    ){
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(220.dp)
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
                .padding(top = 28.dp, end = 22.dp)
                .background(Color(0x33FFFFFF), CircleShape)
                .padding(62.dp)
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
                        text="Shared cart",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="Session: $sessionCode",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
                Box(
                    modifier=Modifier
                        .shadow(4.dp, RoundedCornerShape(18.dp))
                        .background(Color.White, RoundedCornerShape(18.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    Column(
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        Text(
                            text=if(isCartLocked) "STATUS" else "ITEMS",
                            style=MaterialTheme.typography.labelSmall,
                            color=grayText,
                            fontWeight=FontWeight.Bold
                        )
                        Text(
                            text=if(isCartLocked) "LOCKED" else "$totalQuantity",
                            color=if(isCartLocked) orange else green,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.height(18.dp))
            LazyColumn(
                modifier=Modifier.weight(1f)
            ){
                item{
                    CartTopSummaryCard(
                        cartItems=cartItems,
                        totalQuantity=totalQuantity,
                        itemTotal=itemTotal,
                        isCartLocked=isCartLocked,
                        orange=orange,
                        deepOrange=deepOrange,
                        darkText=darkText,
                        grayText=grayText,
                        green=green,
                        softOrange=softOrange
                    )
                    Spacer(modifier=Modifier.height(12.dp))
                }
                if(isCartLocked){
                    item{
                        CartStatusCard(
                            title="Cart is locked",
                            message="Items can no longer be edited. Continue to bill split or checkout.",
                            accentColor=orange,
                            grayText=grayText
                        )
                        Spacer(modifier=Modifier.height(12.dp))
                    }
                }
                if(cartItems.isEmpty()){
                    item{
                        EmptyCartCard(
                            orange=orange,
                            darkText=darkText,
                            grayText=grayText
                        )
                        Spacer(modifier=Modifier.height(12.dp))
                    }
                }else{
                    items(cartItems){item->
                        CartItemCard(
                            item=item,
                            canEdit=item.addedByName==currentUserName&&!isCartLocked,
                            isCartLocked=isCartLocked,
                            onIncreaseClick={
                                onIncreaseQuantity(item.id)
                            },
                            onDecreaseClick={
                                onDecreaseQuantity(item.id)
                            },
                            onRemoveClick={
                                onRemoveItem(item.id)
                            }
                        )
                        Spacer(modifier=Modifier.height(12.dp))
                    }
                }
            }
            Spacer(modifier=Modifier.height(10.dp))
            BillSummaryCard(
                itemTotal=itemTotal,
                deliveryFee=deliveryFee,
                platformFee=platformFee,
                grandTotal=grandTotal,
                orange=orange,
                darkText=darkText,
                grayText=grayText
            )
            Spacer(modifier=Modifier.height(14.dp))
            Button(
                onClick=onContinueToBillSplitClick,
                enabled=cartItems.isNotEmpty(),
                modifier=Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors=ButtonDefaults.buttonColors(
                    containerColor=orange
                ),
                shape=RoundedCornerShape(16.dp)
            ){
                Text(
                    text="Continue to Bill Split",
                    fontWeight=FontWeight.Bold
                )
            }
            Spacer(modifier=Modifier.height(10.dp))
            OutlinedButton(
                onClick=onBackClick,
                modifier=Modifier
                    .fillMaxWidth()
                    .height(54.dp), shape=RoundedCornerShape(16.dp),
                colors=ButtonDefaults.outlinedButtonColors(
                    containerColor=Color.White
                )
            ){
                Text(
                    text="Back",
                    color=orange,
                    fontWeight=FontWeight.Bold
                )
            }
            Spacer(modifier=Modifier.height(14.dp))
        }
    }
}

@Composable
fun CartTopSummaryCard(
    cartItems:List<CartItem>,
    totalQuantity:Int,
    itemTotal:Int,
    isCartLocked:Boolean,
    orange:Color,
    deepOrange:Color,
    darkText:Color,
    grayText:Color,
    green:Color,
    softOrange:Color
){
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .shadow(7.dp, RoundedCornerShape(26.dp)),
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
                verticalAlignment=Alignment.Top
            ){
                Column(
                    modifier=Modifier.weight(1f)
                ){
                    Text(
                        text="Group cart summary",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text=if(cartItems.isEmpty()){
                            "No items added yet"
                        }else{
                            "$totalQuantity item(s) from ${cartItems.map { it.addedByName }.distinct().size} participant(s)"
                        },
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
                Box(
                    modifier=Modifier
                        .background(
                            if(isCartLocked) softOrange else Color(0xFFE9F8EF),
                            RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ){
                    Text(
                        text=if(isCartLocked) "Locked" else "Live sync",
                        color=if(isCartLocked) deepOrange else green,
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
                CartSummaryPill(
                    title="Items",
                    value="$totalQuantity",
                    modifier=Modifier.weight(1f),
                    color=orange
                )
                CartSummaryPill(
                    title="Subtotal",
                    value="₹$itemTotal",
                    modifier=Modifier.weight(1f),
                    color=darkText
                )
            }
        }
    }
}

@Composable
fun CartSummaryPill(
    title:String,
    value:String,
    modifier:Modifier,
    color:Color
){
    Column(
        modifier=modifier
            .background(Color(0xFFFFF7ED), RoundedCornerShape(18.dp))
            .padding(vertical = 10.dp),
        horizontalAlignment=Alignment.CenterHorizontally
    ){
        Text(
            text=title,
            color=Color(0xFF686B78),
            style=MaterialTheme.typography.bodySmall,
            fontWeight=FontWeight.SemiBold
        )
        Text(
            text=value,
            color=color,
            style=MaterialTheme.typography.titleMedium,
            fontWeight=FontWeight.ExtraBold,
            modifier=Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun CartStatusCard(
    title:String,
    message:String,
    accentColor:Color,
    grayText:Color
){
    Card(
        modifier=Modifier.fillMaxWidth(),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        shape=RoundedCornerShape(22.dp),
        elevation=CardDefaults.cardElevation(
            defaultElevation=3.dp
        )
    ){
        Column(
            modifier=Modifier.padding(16.dp)
        ){
            Text(
                text=title,
                fontWeight=FontWeight.ExtraBold,
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
fun EmptyCartCard(
    orange:Color,
    darkText:Color,
    grayText:Color
){
    Card(
        modifier=Modifier.fillMaxWidth(),
        shape=RoundedCornerShape(26.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=5.dp
        )
    ){
        Column(
            modifier=Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment=Alignment.CenterHorizontally
        ){
            Text(
                text="🛒",
                style=MaterialTheme.typography.displaySmall
            )
            Text(
                text="Your cart is empty",
                style=MaterialTheme.typography.titleMedium,
                fontWeight=FontWeight.ExtraBold,
                color=darkText,
                modifier=Modifier.padding(top = 8.dp)
            )
            Text(
                text="Go back to the menu and add dishes with your group.",
                style=MaterialTheme.typography.bodyMedium,
                color=grayText,
                modifier=Modifier.padding(top = 6.dp)
            )
            Spacer(modifier=Modifier.height(12.dp))
            Box(
                modifier=Modifier
                    .background(Color(0xFFFFE8D2), RoundedCornerShape(50.dp))
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            ){
                Text(
                    text="Start ordering",
                    color=orange,
                    fontWeight=FontWeight.Bold,
                    style=MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun CartItemCard(
    item:CartItem,
    canEdit:Boolean,
    isCartLocked:Boolean,
    onIncreaseClick:()->Unit,
    onDecreaseClick:()->Unit,
    onRemoveClick:()->Unit
){
    val orange=Color(0xFFFC8019)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val softOrange=Color(0xFFFFE8D2)

    Card(
        modifier=Modifier.fillMaxWidth(),
        shape=RoundedCornerShape(26.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ), elevation=CardDefaults.cardElevation(
            defaultElevation=4.dp
        )
    ){
        Column(
            modifier=Modifier.padding(16.dp)
        ){
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.Top
            ){
                Row(
                    modifier=Modifier.weight(1f),
                    verticalAlignment=Alignment.Top
                ){
                    Box(
                        modifier=Modifier
                            .background(softOrange, RoundedCornerShape(16.dp))
                            .padding(horizontal = 11.dp, vertical = 9.dp)
                    ){
                        Text(
                            text="ITEM",
                            color=orange,
                            fontWeight=FontWeight.ExtraBold,
                            style=MaterialTheme.typography.labelSmall
                        )
                    }
                    Column(
                        modifier=Modifier
                            .weight(1f)
                            .padding(start = 12.dp)
                    ){
                        Text(
                            text=item.name,
                            style=MaterialTheme.typography.bodyLarge,
                            fontWeight=FontWeight.ExtraBold,
                            color=darkText
                        )
                        if(item.description.isNotBlank()){
                            Box(
                                modifier=Modifier
                                    .padding(top = 7.dp)
                                    .background(Color(0xFFFFF7ED), RoundedCornerShape(14.dp))
                                    .padding(horizontal = 10.dp, vertical = 8.dp)
                            ){
                                Text(
                                    text=item.description,
                                    style=MaterialTheme.typography.bodySmall,
                                    color=orange,
                                    fontWeight=FontWeight.SemiBold
                                )
                            }
                        }
                        Text(
                            text="Added by ${item.addedByName}",
                            style=MaterialTheme.typography.bodySmall,
                            color=grayText,
                            modifier=Modifier.padding(top = 7.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment=Alignment.End
                ){
                    Text(
                        text="₹${item.price * item.quantity}",
                        style=MaterialTheme.typography.bodyLarge,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="₹${item.price} each",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 3.dp)
                    )
                }
            }
            Spacer(modifier=Modifier.height(14.dp))
            if(canEdit){
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    QuantityStepper(
                        quantity=item.quantity,
                        orange=orange,
                        darkText=darkText,
                        onDecreaseClick=onDecreaseClick,
                        onIncreaseClick=onIncreaseClick
                    )
                    OutlinedButton(
                        onClick=onRemoveClick,
                        shape=RoundedCornerShape(14.dp)
                    ){
                        Text(
                            text="Remove",
                            color=orange,
                            fontWeight=FontWeight.Bold
                        )
                    }
                }
            }else{
                Box(
                    modifier=Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF7ED), RoundedCornerShape(14.dp))
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                ){
                    Text(
                        text=if (isCartLocked) {
                            "Cart is locked. This item cannot be edited."
                        }else{
                            "Only ${item.addedByName} can edit this item"
                        },
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        fontWeight=FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun QuantityStepper(
    quantity:Int,
    orange:Color,
    darkText:Color,
    onDecreaseClick:()->Unit,
    onIncreaseClick:()->Unit
){
    Row(
        verticalAlignment=Alignment.CenterVertically,
        modifier=Modifier
            .background(Color(0xFFFFF7ED), RoundedCornerShape(16.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ){
        OutlinedButton(
            onClick=onDecreaseClick,
            shape=RoundedCornerShape(12.dp)
        ){
            Text(
                text="-",
                color=orange,
                fontWeight=FontWeight.ExtraBold
            )
        }
        Text(
            text="$quantity",
            modifier=Modifier.padding(horizontal = 12.dp),
            fontWeight=FontWeight.ExtraBold,
            color=darkText
        )
        OutlinedButton(
            onClick=onIncreaseClick,
            shape=RoundedCornerShape(12.dp)
        ){
            Text(
                text="+",
                color=orange,
                fontWeight=FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun BillSummaryCard(
    itemTotal:Int,
    deliveryFee:Int,
    platformFee:Int,
    grandTotal:Int,
    orange:Color,
    darkText:Color,
    grayText:Color
){
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .shadow(
                elevation=7.dp,
                shape=RoundedCornerShape(24.dp)
            ), shape=RoundedCornerShape(24.dp), colors=CardDefaults.cardColors(
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
                Column{
                    Text(
                        text="Bill Summary",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="Before personal split",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 3.dp)
                    )
                }
                Text(
                    text="₹$grandTotal",
                    style=MaterialTheme.typography.titleLarge,
                    fontWeight=FontWeight.ExtraBold,
                    color=orange
                )
            }
            Spacer(modifier=Modifier.height(14.dp))
            BillRow(label="Item Total",amount=itemTotal)
            BillRow(label="Delivery Fee",amount=deliveryFee)
            BillRow(label="Platform Fee",amount=platformFee)
            Spacer(modifier=Modifier.height(10.dp))
            BillRow(
                label="Grand Total",
                amount=grandTotal,
                isBold=true
            )
        }
    }
}

@Composable
fun BillRow(
    label: String,
    amount:Int,
    isBold:Boolean=false
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)

    Row(
        modifier=Modifier
            .fillMaxWidth()
            .padding(vertical=4.dp),
        horizontalArrangement=Arrangement.SpaceBetween
    ){
        Text(
            text=label,
            color=if(isBold) darkText else grayText,
            fontWeight=if(isBold){
                FontWeight.Bold
            }else{
                FontWeight.Normal
            }
        )
        Text(
            text="₹$amount",
            color=darkText,
            fontWeight=if(isBold){
                FontWeight.Bold
            }else{
                FontWeight.Normal
            }
        )
    }
}