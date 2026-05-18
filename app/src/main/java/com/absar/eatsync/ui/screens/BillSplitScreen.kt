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
import com.absar.eatsync.model.Participant
import com.absar.eatsync.model.UserBill

@Composable
fun BillSplitScreen(
    sessionCode: String,
    cartItems: List<CartItem>,
    participants: List<Participant>,
    currentUserName: String,
    isCartLocked: Boolean,
    onToggleReady: (String) -> Unit,
    onLockCartClick: () -> Unit,
    onUnlockCartClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    onBackClick: () -> Unit
){
    val deliveryFee=if(cartItems.isEmpty()) 0 else 40
    val platformFee=if(cartItems.isEmpty()) 0 else 10

    val totalSharedCharges=deliveryFee+platformFee

    val sharedChargePerUser=if(participants.isNotEmpty()){
        totalSharedCharges/participants.size
    }else{
        0
    }

    val userBills=participants.map{participant->
        val itemTotal=cartItems
            .filter{it.addedByName==participant.name}
            .sumOf{it.price*it.quantity}

        UserBill(
            userName=participant.name,
            itemTotal=itemTotal,
            sharedCharges=sharedChargePerUser,
            finalAmount=itemTotal+sharedChargePerUser
        )
    }

    val grandTotal=userBills.sumOf{it.finalAmount}
    val readyCount=participants.count{it.ready}
    val allReady=participants.isNotEmpty()&&readyCount==participants.size
    val currentUser=participants.firstOrNull{it.name==currentUserName}
    val isCurrentUserHost=currentUser?.host==true

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
                        text="Bill split",
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
                        text=if(isCartLocked) "LOCKED" else "$readyCount/${participants.size} READY",
                        color=if(isCartLocked) orange else green,
                        fontWeight=FontWeight.ExtraBold,
                        style=MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier=Modifier.height(14.dp))

            Card(
                modifier=Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(22.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ){
                Column(
                    modifier=Modifier.padding(16.dp)
                ){
                    Text(
                        text="Split rule",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )

                    Text(
                        text="Food is paid by the person who added it. Delivery and platform fees are shared equally.",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 6.dp)
                    )
                }
            }

            Spacer(modifier=Modifier.height(14.dp))

            LazyColumn(
                modifier=Modifier.weight(1f)
            ){
                items(userBills){bill->
                    val participant=participants.firstOrNull{it.name==bill.userName}
                    val userCartItems=cartItems.filter{it.addedByName==bill.userName}

                    UserBillCard(
                        bill=bill,
                        userCartItems=userCartItems,
                        isReady=participant?.ready==true,
                        canToggleReady=bill.userName==currentUserName&&!isCartLocked,
                        isCartLocked=isCartLocked,
                        onReadyClick={
                            onToggleReady(bill.userName)
                        }
                    )

                    Spacer(modifier=Modifier.height(12.dp))
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
                                text="Final Summary",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )

                            Text(
                                text=if(allReady){
                                    "Everyone is ready."
                                }else{
                                    "$readyCount/${participants.size} users ready"
                                },
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

                    BillSplitRow(label="Delivery Fee",amount=deliveryFee)
                    BillSplitRow(label="Platform Fee",amount=platformFee)
                    BillSplitRow(label="Grand Total",amount=grandTotal,isBold=true)

                    Spacer(modifier=Modifier.height(14.dp))

                    if(isCartLocked){
                        Text(
                            text="Cart is locked. No further cart changes allowed.",
                            style=MaterialTheme.typography.bodyMedium,
                            color=darkText,
                            fontWeight=FontWeight.Bold
                        )

                        Spacer(modifier=Modifier.height(10.dp))

                        Button(
                            onClick=onCheckoutClick,
                            modifier=Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors=ButtonDefaults.buttonColors(
                                containerColor=orange
                            ),
                            shape=RoundedCornerShape(16.dp)
                        ){
                            Text(
                                text=if(isCurrentUserHost){
                                    "Open Checkout"
                                }else{
                                    "View Checkout Status"
                                },
                                fontWeight=FontWeight.Bold
                            )
                        }

                        if(isCurrentUserHost){
                            Spacer(modifier=Modifier.height(10.dp))

                            OutlinedButton(
                                onClick=onUnlockCartClick,
                                modifier=Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape=RoundedCornerShape(16.dp)
                            ){
                                Text(
                                    text="Unlock Cart",
                                    color=orange,
                                    fontWeight=FontWeight.Bold
                                )
                            }
                        }
                    }else{
                        if(allReady&&isCurrentUserHost){
                            Button(
                                onClick=onLockCartClick,
                                modifier=Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                colors=ButtonDefaults.buttonColors(
                                    containerColor=orange
                                ),
                                shape=RoundedCornerShape(16.dp)
                            ){
                                Text(
                                    text="Lock Cart & Checkout",
                                    fontWeight=FontWeight.Bold
                                )
                            }
                        }else if(allReady&&!isCurrentUserHost){
                            Text(
                                text="Everyone is ready. Waiting for host to lock cart.",
                                style=MaterialTheme.typography.bodyMedium,
                                color=darkText,
                                fontWeight=FontWeight.Bold
                            )
                        }else{
                            Text(
                                text="Ask everyone to mark ready before checkout.",
                                style=MaterialTheme.typography.bodyMedium,
                                color=grayText,
                                fontWeight=FontWeight.SemiBold
                            )
                        }
                    }
                }
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
fun UserBillCard(
    bill:UserBill,
    userCartItems:List<CartItem>,
    isReady:Boolean,
    canToggleReady:Boolean,
    isCartLocked:Boolean,
    onReadyClick:() -> Unit
){
    val orange=Color(0xFFFC8019)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)

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
                verticalAlignment=Alignment.CenterVertically
            ){
                Column{
                    Text(
                        text=bill.userName,
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )

                    Text(
                        text=if(isReady) "Ready for checkout" else "Not ready yet",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 3.dp)
                    )
                }

                Text(
                    text="₹${bill.finalAmount}",
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.ExtraBold,
                    color=darkText
                )
            }
            Spacer(modifier=Modifier.height(10.dp))
            if(userCartItems.isNotEmpty()){
                Text(
                    text="Items added",
                    style=MaterialTheme.typography.bodySmall,
                    color=orange,
                    fontWeight=FontWeight.Bold
                )
                Spacer(modifier=Modifier.height(6.dp))
                userCartItems.forEach{item->
                    BillItemRow(item=item)
                    Spacer(modifier=Modifier.height(8.dp))
                }
                Spacer(modifier=Modifier.height(4.dp))
            }
            BillSplitRow(label="Item total",amount=bill.itemTotal)
            BillSplitRow(label="Shared charges",amount=bill.sharedCharges)

            Spacer(modifier=Modifier.height(12.dp))

            if(canToggleReady){
                Button(
                    onClick=onReadyClick,
                    modifier=Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors=ButtonDefaults.buttonColors(
                        containerColor=if(isReady) green else orange
                    ),
                    shape=RoundedCornerShape(14.dp)
                ){
                    Text(
                        text=if(isReady) "Ready ✓" else "Mark Ready",
                        fontWeight=FontWeight.Bold
                    )
                }
            }else{
                Box(
                    modifier=Modifier
                        .background(
                            if(isReady) Color(0xFFE9F8EF) else Color(0xFFFFF3E8),
                            RoundedCornerShape(14.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                ){
                    Text(
                        text=if(isCartLocked&&isReady){
                            "Ready ✓"
                        }else if(isCartLocked&&!isReady){
                            "Cart locked"
                        }else if(isReady){
                            "Ready ✓"
                        }else{
                            "Waiting"
                        },
                        color=if(isReady) green else orange,
                        style=MaterialTheme.typography.bodySmall,
                        fontWeight=FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BillItemRow(
    item:CartItem
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val orange=Color(0xFFFC8019)

    Column(
        modifier=Modifier
            .fillMaxWidth()
            .background(
                color=Color(0xFFFFF7ED),
                shape=RoundedCornerShape(14.dp)
            )
            .padding(10.dp)
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
                    style=MaterialTheme.typography.bodyMedium,
                    fontWeight=FontWeight.Bold,
                    color=darkText
                )
                if(item.description.isNotBlank()){
                    Text(
                        text=item.description,
                        style=MaterialTheme.typography.bodySmall,
                        color=orange,
                        fontWeight=FontWeight.SemiBold,
                        modifier=Modifier.padding(top = 3.dp)
                    )
                }
                Text(
                    text="₹${item.price} × ${item.quantity}",
                    style=MaterialTheme.typography.bodySmall,
                    color=grayText,
                    modifier=Modifier.padding(top = 3.dp)
                )
            }

            Text(
                text="₹${item.price*item.quantity}",
                style=MaterialTheme.typography.bodyMedium,
                fontWeight=FontWeight.Bold,
                color=darkText,
                modifier=Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun BillSplitRow(
    label:String,
    amount:Int,
    isBold:Boolean=false
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)

    Row(
        modifier=Modifier
            .fillMaxWidth()
            .padding(vertical=3.dp),
        horizontalArrangement=Arrangement.SpaceBetween
    ){
        Text(
            text=label,
            color=if(isBold) darkText else grayText,
            fontWeight=if(isBold) FontWeight.Bold else FontWeight.Normal
        )

        Text(
            text="₹$amount",
            color=darkText,
            fontWeight=if(isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}