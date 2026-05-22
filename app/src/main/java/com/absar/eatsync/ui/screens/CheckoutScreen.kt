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
import com.absar.eatsync.model.Participant

@Composable
fun CheckoutScreen(
    sessionCode:String,
    cartItems:List<CartItem>,
    participants:List<Participant>,
    currentUserName:String,
    onBackClick:()->Unit
){
    val itemTotal=cartItems.sumOf{it.price*it.quantity}
    val deliveryFee=if(cartItems.isEmpty()) 0 else 40
    val platformFee=if(cartItems.isEmpty()) 0 else 10
    val grandTotal=itemTotal+deliveryFee+platformFee
    val totalQuantity=cartItems.sumOf { it.quantity }

    val currentUser=participants.firstOrNull{it.name==currentUserName}
    val isHost=currentUser?.host==true

    val orange=Color(0xFFFC8019)
    val deepOrange=Color(0xFFE95D00)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)
    val softOrange=Color(0xFFFFE8D2)

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
                        text="Checkout",
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
                            text="CART",
                            style=MaterialTheme.typography.labelSmall,
                            color=grayText,
                            fontWeight=FontWeight.Bold
                        )
                        Text(
                            text="LOCKED",
                            color=green,
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
                    FinalOrderSummaryCard(
                        itemTotal=itemTotal,
                        deliveryFee=deliveryFee,
                        platformFee=platformFee,
                        grandTotal=grandTotal,
                        totalQuantity=totalQuantity,
                        cartItemsCount=cartItems.size,
                        orange=orange,
                        deepOrange=deepOrange,
                        darkText=darkText,
                        grayText=grayText,
                        softOrange=softOrange
                    )
                    Spacer(modifier=Modifier.height(14.dp))
                }
                item{
                    CheckoutStatusCard(
                        isHost=isHost,
                        orange=orange,
                        deepOrange=deepOrange,
                        darkText=darkText,
                        grayText=grayText,
                        softOrange=softOrange
                    )
                    Spacer(modifier=Modifier.height(14.dp))
                }
                item{
                    CheckoutParticipantsCard(
                        participants=participants,
                        cartItems=cartItems,
                        sharedCharges=deliveryFee+platformFee,
                        orange=orange,
                        darkText=darkText,
                        grayText=grayText,
                        green=green
                    )
                    Spacer(modifier=Modifier.height(14.dp))
                }
                if(cartItems.isNotEmpty()){
                    item{
                        LockedCartPreviewCard(
                            cartItems=cartItems,
                            orange=orange,
                            darkText=darkText,
                            grayText=grayText
                        )
                        Spacer(modifier=Modifier.height(14.dp))
                    }
                }
            }
            if(isHost){
                Button(
                    onClick={},
                    enabled=false,
                    modifier=Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors=ButtonDefaults.buttonColors(
                        disabledContainerColor=Color(0xFFFFD7B3),
                        disabledContentColor=Color.White
                    ),
                    shape=RoundedCornerShape(16.dp)
                ){
                    Text(
                        text="Place Order Coming Soon",
                        fontWeight=FontWeight.Bold
                    )
                }
                Spacer(modifier=Modifier.height(10.dp))
            }
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
fun FinalOrderSummaryCard(
    itemTotal:Int,
    deliveryFee:Int,
    platformFee:Int,
    grandTotal:Int,
    totalQuantity:Int,
    cartItemsCount:Int,
    orange:Color,
    deepOrange:Color,
    darkText:Color,
    grayText:Color,
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
                        text="Final order summary",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="$totalQuantity item(s) locked across $cartItemsCount cart line(s)",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
                Box(
                    modifier=Modifier
                        .background(softOrange, RoundedCornerShape(50.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ){
                    Text(
                        text="Ready",
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
                CheckoutPill(
                    title="Items",
                    value="₹$itemTotal",
                    modifier=Modifier.weight(1f),
                    color=darkText
                )
                CheckoutPill(
                    title="Fees",
                    value="₹${deliveryFee+platformFee}",
                    modifier=Modifier.weight(1f),
                    color=orange
                )
                CheckoutPill(
                    title="Total",
                    value="₹$grandTotal",
                    modifier=Modifier.weight(1f),
                    color=orange
                )
            }
            Spacer(modifier=Modifier.height(14.dp))
            CheckoutRow(label="Item Total",amount=itemTotal)
            CheckoutRow(label="Delivery Fee",amount=deliveryFee)
            CheckoutRow(label="Platform Fee",amount=platformFee)
            Spacer(modifier=Modifier.height(8.dp))
            CheckoutRow(
                label="Grand Total",
                amount=grandTotal,
                isBold=true
            )
        }
    }
}

@Composable
fun CheckoutPill(
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
fun CheckoutStatusCard(
    isHost:Boolean,
    orange:Color,
    deepOrange:Color,
    darkText:Color,
    grayText:Color,
    softOrange:Color
){
    Card(
        modifier=Modifier.fillMaxWidth(),
        shape=RoundedCornerShape(26.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=4.dp
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
                        text=if(isHost){
                            "Host checkout"
                        }else{
                            "Waiting for host"
                        },
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text=if(isHost){
                            "Your cart is locked and ready. The next production step is to connect this locked cart to Swiggy MCP cart/order flow."
                        }else{
                            "Your item and split are included. The host will handle the final order action."
                        },
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 8.dp)
                    )
                }
                Box(
                    modifier=Modifier
                        .background(softOrange, RoundedCornerShape(50.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ){
                    Text(
                        text=if(isHost) "Host" else "Member",
                        color=deepOrange,
                        style=MaterialTheme.typography.bodySmall,
                        fontWeight=FontWeight.Bold
                    )
                }
            }
            Spacer(modifier=Modifier.height(14.dp))
            Box(
                modifier=Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF7ED), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ){
                Text(
                    text=if(isHost){
                        "MVP safety note: real order placement is intentionally disabled until Swiggy MCP cart/order permissions and safe checkout handling are finalized."
                    }else{
                        "You can relax now. Your amount is already included in the final group split."
                    },
                    color=if(isHost) orange else grayText,
                    fontWeight=FontWeight.SemiBold,
                    style=MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun CheckoutParticipantsCard(
    participants:List<Participant>,
    cartItems:List<CartItem>,
    sharedCharges:Int,
    orange:Color,
    darkText:Color,
    grayText:Color,
    green:Color
){
    val sharedChargePerUser=if(participants.isNotEmpty()){
        sharedCharges/participants.size
    }else{
        0
    }
    Card(
        modifier=Modifier.fillMaxWidth(),
        shape=RoundedCornerShape(26.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=4.dp
        )
    ){
        Column(
            modifier=Modifier.padding(18.dp)
        ){
            Text(
                text="People in order",
                style=MaterialTheme.typography.titleMedium,
                fontWeight=FontWeight.ExtraBold,
                color=darkText
            )
            Text(
                text="${participants.size} participant(s) included in this checkout.",
                style=MaterialTheme.typography.bodySmall,
                color=grayText,
                modifier=Modifier.padding(top = 5.dp)
            )
            Spacer(modifier=Modifier.height(12.dp))
            participants.forEach{participant->
                val userItemTotal=cartItems
                    .filter { it.addedByName==participant.name }
                    .sumOf { it.price*it.quantity }
                val finalAmount=userItemTotal+sharedChargePerUser
                CheckoutParticipantRow(
                    participant=participant,
                    userItemTotal=userItemTotal,
                    sharedCharge=sharedChargePerUser,
                    finalAmount=finalAmount,
                    orange=orange,
                    darkText=darkText,
                    grayText=grayText,
                    green=green
                )
                Spacer(modifier=Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun LockedCartPreviewCard(
    cartItems:List<CartItem>,
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
            defaultElevation=4.dp
        )
    ){
        Column(
            modifier=Modifier.padding(18.dp)
        ){
            Text(
                text="Locked cart preview",
                style=MaterialTheme.typography.titleMedium,
                fontWeight=FontWeight.ExtraBold,
                color=darkText
            )
            Text(
                text="Final items included in this checkout.",
                style=MaterialTheme.typography.bodySmall,
                color=grayText,
                modifier=Modifier.padding(top = 5.dp)
            )
            Spacer(modifier=Modifier.height(12.dp))
            cartItems.forEach{item->
                CheckoutCartItemRow(
                    item=item,
                    orange=orange,
                    darkText=darkText,
                    grayText=grayText
                )
                Spacer(modifier=Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CheckoutCartItemRow(
    item:CartItem,
    orange:Color,
    darkText:Color,
    grayText:Color
){
    Column(
        modifier=Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF7ED), RoundedCornerShape(16.dp))
            .padding(12.dp)
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
                    text=item.name,
                    color=darkText,
                    fontWeight=FontWeight.ExtraBold,
                    style=MaterialTheme.typography.bodyMedium
                )
                if(item.description.isNotBlank()){
                    Text(
                        text=item.description,
                        color=orange,
                        fontWeight=FontWeight.SemiBold,
                        style=MaterialTheme.typography.bodySmall,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
                Text(
                    text="Added by ${item.addedByName} • ₹${item.price} × ${item.quantity}",
                    color=grayText,
                    style=MaterialTheme.typography.bodySmall,
                    modifier=Modifier.padding(top = 4.dp)
                )
            }
            Text(
                text="₹${item.price*item.quantity}",
                color=darkText,
                fontWeight=FontWeight.ExtraBold,
                style=MaterialTheme.typography.bodyMedium,
                modifier=Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun CheckoutRow(
    label:String,
    amount:Int,
    isBold:Boolean=false
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    Row(
        modifier=Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement=Arrangement.SpaceBetween
    ){
        Text(
            text=label,
            color=if(isBold) darkText else grayText,
            fontWeight=if(isBold){
                FontWeight.Bold
            }
            else{
                FontWeight.Normal
            }
        )
        Text(
            text="₹$amount",
            color=darkText,
            fontWeight=if(isBold){
                FontWeight.Bold
            }
            else{
                FontWeight.Normal
            }
        )
    }
}

@Composable
fun CheckoutParticipantRow(
    participant:Participant,
    userItemTotal:Int,
    sharedCharge:Int,
    finalAmount:Int,
    orange:Color,
    darkText:Color,
    grayText:Color,
    green:Color
){
    Row(
        modifier=Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF7ED), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalArrangement=Arrangement.SpaceBetween,
        verticalAlignment=Alignment.CenterVertically
    ){
        Column(
            modifier=Modifier.weight(1f)
        ){
            Text(
                text=participant.name,
                color=darkText,
                fontWeight=FontWeight.ExtraBold,
                style=MaterialTheme.typography.bodyMedium
            )
            Text(
                text=if(participant.host){
                    "Host"
                }else{
                    "Participant"
                },
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 2.dp)
            )
            Text(
                text="Items ₹$userItemTotal + Shared ₹$sharedCharge",
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 4.dp)
            )
        }
        Column(
            horizontalAlignment=Alignment.End
        ){
            Text(
                text="₹$finalAmount",
                color=darkText,
                fontWeight=FontWeight.ExtraBold,
                style=MaterialTheme.typography.titleMedium
            )
            Box(
                modifier=Modifier
                    .padding(top = 5.dp)
                    .background(
                        if(participant.ready) Color(0xFFE9F8EF) else Color(0xFFFFE8D2),
                        RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal=10.dp,vertical=5.dp)
            ){
                Text(
                    text=if(participant.ready){
                        "READY"
                    }else{
                        "LOCKED"
                    },
                    color=if(participant.ready) green else orange,
                    fontWeight=FontWeight.Bold,
                    style=MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}