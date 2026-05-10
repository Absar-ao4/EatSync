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
    val currentUser=participants.firstOrNull{it.name==currentUserName}
    val isHost=currentUser?.host==true

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
                        .background(Color(0xFFE9F8EF), RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    Text(
                        text="LOCKED",
                        color=green,
                        fontWeight=FontWeight.ExtraBold,
                        style=MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier=Modifier.height(18.dp))
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
                    modifier=Modifier.padding(20.dp)
                ){
                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement=Arrangement.SpaceBetween,
                        verticalAlignment=Alignment.CenterVertically
                    ){
                        Column{
                            Text(
                                text="Final Order Summary",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )

                            Text(
                                text="${cartItems.size} items in locked cart",
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
                    Spacer(modifier=Modifier.height(16.dp))

                    CheckoutRow(label="Item Total",amount=itemTotal)
                    CheckoutRow(label="Delivery Fee",amount=deliveryFee)
                    CheckoutRow(label="Platform Fee",amount=platformFee)

                    Spacer(modifier=Modifier.height(10.dp))
                    CheckoutRow(
                        label="Grand Total",
                        amount=grandTotal,
                        isBold=true
                    )
                }
            }
            Spacer(modifier=Modifier.height(16.dp))
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
                    modifier=Modifier.padding(18.dp)
                ){
                    Text(
                        text=if(isHost){
                            "Host checkout"
                        }
                        else{
                            "Waiting for host"
                        },
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )
                    Text(
                        text=if(isHost){
                            "Cart is locked. Next, this final cart will be synced with Swiggy MCP and placed from the host account."
                        }
                        else{
                            "Cart is locked. The host will complete checkout and place the final order."
                        },
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 8.dp)
                    )
                    Spacer(modifier=Modifier.height(16.dp))
                    Box(
                        modifier=Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFF3E8), RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ){
                        Text(
                            text=if(isHost){
                                "Next build step: connect locked cart with Swiggy Food MCP checkout flow."
                            }
                            else{
                                "You can relax now. Your amount is already included in the final split."
                            },
                            color=orange,
                            fontWeight=FontWeight.Bold,
                            style=MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.height(16.dp))
            Card(
                modifier=Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(22.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ){
                Column(
                    modifier=Modifier.padding(18.dp)
                ){
                    Text(
                        text="People in order",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )
                    Text(
                        text="${participants.size} participants included in this checkout.",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 5.dp)
                    )
                    Spacer(modifier=Modifier.height(12.dp))
                    participants.forEach{participant->
                        CheckoutParticipantRow(
                            participant=participant
                        )

                        Spacer(modifier=Modifier.height(8.dp))
                    }
                }
            }
            Spacer(modifier=Modifier.weight(1f))
            if(isHost){
                Button(
                    onClick={},
                    enabled=false,
                    modifier=Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors=ButtonDefaults.buttonColors(
                        containerColor=orange
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
    participant:Participant
){
    val orange=Color(0xFFFC8019)
    val green=Color(0xFF48C479)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)

    Row(
        modifier=Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFAF5), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalArrangement=Arrangement.SpaceBetween,
        verticalAlignment=Alignment.CenterVertically
    ){
        Column{
            Text(
                text=participant.name,
                color=darkText,
                fontWeight=FontWeight.Bold,
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
        }
        Box(
            modifier=Modifier
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