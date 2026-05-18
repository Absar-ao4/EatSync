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
    val deliveryFee=
        if(cartItems.isEmpty()){0}
        else{40}
    val platformFee=
        if(cartItems.isEmpty()){0}
        else{10}
    val grandTotal=itemTotal+deliveryFee+platformFee

    val orange=Color(0xFFFC8019)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)

    Box(
        modifier=Modifier
            .fillMaxSize()
            .background(background)
            .padding(20.dp)
    ){
        Box(
            modifier=Modifier
                .align(Alignment.TopEnd)
                .background(Color(0xFFFFE4C7), CircleShape)
                .padding(66.dp)
        )

        Column(
            modifier=Modifier.fillMaxSize()
        ){
            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically
            ){
                Column{
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
                        .background(
                            if(isCartLocked) Color(0xFFFFE8D2) else Color(0xFFE9F8EF),
                            RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    Text(
                        text=if(isCartLocked) "LOCKED"
                        else "${cartItems.size} ITEMS",
                        color=if(isCartLocked) orange
                        else green,
                        fontWeight=FontWeight.ExtraBold,
                        style=MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier=Modifier.height(16.dp))

            if(isCartLocked){
                Card(
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(22.dp),
                    colors=CardDefaults.cardColors(
                        containerColor=Color.White
                    )
                ){
                    Text(
                        text="Cart is locked. Items can no longer be edited.",
                        modifier=Modifier.padding(16.dp),
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )
                }

                Spacer(modifier=Modifier.height(14.dp))
            }

            if(cartItems.isEmpty()){
                Card(
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(22.dp),
                    colors=CardDefaults.cardColors(
                        containerColor=Color.White
                    ),
                    elevation=CardDefaults.cardElevation(
                        defaultElevation=4.dp
                    )
                ){
                    Column(
                        modifier=Modifier.padding(20.dp),
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        Text(
                            text="Your cart is empty",
                            style=MaterialTheme.typography.titleMedium,
                            fontWeight=FontWeight.Bold,
                            color=darkText
                        )

                        Text(
                            text="Go back to the menu and add some dishes.",
                            style=MaterialTheme.typography.bodyMedium,
                            color=grayText,
                            modifier=Modifier.padding(top = 6.dp)
                        )
                    }
                }

                Spacer(modifier=Modifier.weight(1f))
            }
            else{
                LazyColumn(
                    modifier=Modifier.weight(1f)
                ){
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

            Spacer(modifier=Modifier.height(12.dp))

            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation=7.dp,
                        shape=RoundedCornerShape(24.dp)
                    ),
                shape=RoundedCornerShape(24.dp),
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
                        Column{
                            Text(
                                text="Bill Summary",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
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
                    .height(54.dp),
                shape=RoundedCornerShape(16.dp)
            ){
                Text(
                    text="Back",
                    color=orange,
                    fontWeight=FontWeight.Bold
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

    Card(
        modifier=Modifier.fillMaxWidth(),
        shape=RoundedCornerShape(22.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=3.dp
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
                            .background(Color(0xFFFFE8D2), RoundedCornerShape(14.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ){
                        Text(
                            text="ITEM",
                            color=orange,
                            fontWeight=FontWeight.Bold,
                            style=MaterialTheme.typography.bodySmall
                        )
                    }

                    Column(
                        modifier=Modifier.padding(start = 12.dp)
                    ){
                        Text(
                            text=item.name,
                            style=MaterialTheme.typography.bodyLarge,
                            fontWeight=FontWeight.Bold,
                            color=darkText
                        )

                        if(item.description.isNotBlank()){
                            Text(
                                text=item.description,
                                style=MaterialTheme.typography.bodySmall,
                                color=orange,
                                fontWeight=FontWeight.SemiBold,
                                modifier=Modifier.padding(top = 4.dp)
                            )
                        }

                        Text(
                            text="Added by ${item.addedByName}",
                            style=MaterialTheme.typography.bodySmall,
                            color=grayText,
                            modifier=Modifier.padding(top = 5.dp)
                        )

                        Text(
                            text="₹${item.price} each",
                            style=MaterialTheme.typography.bodySmall,
                            color=grayText,
                            modifier=Modifier.padding(top = 3.dp)
                        )
                    }
                }

                Text(
                    text="₹${item.price * item.quantity}",
                    style=MaterialTheme.typography.bodyLarge,
                    fontWeight=FontWeight.ExtraBold,
                    color=darkText
                )
            }

            Spacer(modifier=Modifier.height(14.dp))

            if(canEdit){
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Row(
                        verticalAlignment=Alignment.CenterVertically
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
                            text="${item.quantity}",
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
                                color=orange
                            )
                        }
                    }

                    OutlinedButton(
                        onClick=onRemoveClick,
                        shape=RoundedCornerShape(12.dp)
                    ){
                        Text(
                            text="Remove",
                            color=orange
                        )
                    }
                }
            }
            else{
                Text(
                    text=if (isCartLocked) {
                        "Cart is locked. This item cannot be edited."
                    }
                    else{
                        "Only ${item.addedByName} can edit this item"
                    },
                    style=MaterialTheme.typography.bodySmall,
                    color=grayText,
                    fontWeight=FontWeight.SemiBold,
                    modifier=Modifier.padding(top = 4.dp)
                )
            }
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