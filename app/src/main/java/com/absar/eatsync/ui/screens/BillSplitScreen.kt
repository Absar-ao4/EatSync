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
    val itemTotal=cartItems.sumOf { it.price*it.quantity }
    val readyCount=participants.count{it.ready}
    val allReady=participants.isNotEmpty()&&readyCount==participants.size
    val currentUser=participants.firstOrNull{it.name==currentUserName}
    val isCurrentUserHost=currentUser?.host==true

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
                        .shadow(4.dp, RoundedCornerShape(18.dp))
                        .background(Color.White, RoundedCornerShape(18.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    Column(
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        Text(
                            text=if(isCartLocked) "STATUS" else "READY",
                            style=MaterialTheme.typography.labelSmall,
                            color=grayText,
                            fontWeight=FontWeight.Bold
                        )
                        Text(
                            text=if(isCartLocked) "LOCKED" else "$readyCount/${participants.size}",
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
                    SplitRuleCard(
                        itemTotal=itemTotal,
                        sharedCharges=totalSharedCharges,
                        participantsCount=participants.size,
                        orange=orange,
                        deepOrange=deepOrange,
                        darkText=darkText,
                        grayText=grayText,
                        softOrange=softOrange
                    )
                    Spacer(modifier=Modifier.height(12.dp))
                }
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
            Spacer(modifier=Modifier.height(10.dp))
            FinalSummaryCard(
                deliveryFee=deliveryFee,
                platformFee=platformFee,
                grandTotal=grandTotal,
                readyCount=readyCount,
                participantsCount=participants.size,
                allReady=allReady,
                isCartLocked=isCartLocked,
                isCurrentUserHost=isCurrentUserHost,
                orange=orange,
                darkText=darkText,
                grayText=grayText,
                onCheckoutClick=onCheckoutClick,
                onLockCartClick=onLockCartClick,
                onUnlockCartClick=onUnlockCartClick
            )
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
fun SplitRuleCard(
    itemTotal:Int,
    sharedCharges:Int,
    participantsCount:Int,
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
                        text="Split rule",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="Food is paid by the person who added it. Delivery and platform fees are shared equally.",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 5.dp)
                    )
                }
                Box(
                    modifier=Modifier
                        .background(softOrange, RoundedCornerShape(50.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ){
                    Text(
                        text="Fair split",
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
                SplitSummaryPill(
                    title="Items",
                    value="₹$itemTotal",
                    modifier=Modifier.weight(1f),
                    color=darkText
                )
                SplitSummaryPill(
                    title="Shared",
                    value="₹$sharedCharges",
                    modifier=Modifier.weight(1f),
                    color=orange
                )
                SplitSummaryPill(
                    title="Users",
                    value="$participantsCount",
                    modifier=Modifier.weight(1f),
                    color=orange
                )
            }
        }
    }
}

@Composable
fun SplitSummaryPill(
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
        shape=RoundedCornerShape(26.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
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
                Column(
                    modifier=Modifier.weight(1f)
                ){
                    Row(
                        verticalAlignment=Alignment.CenterVertically
                    ){
                        Text(
                            text=bill.userName,
                            style=MaterialTheme.typography.titleMedium,
                            fontWeight=FontWeight.ExtraBold,
                            color=darkText
                        )
                        Spacer(modifier=Modifier.padding(horizontal = 4.dp))
                        StatusPill(
                            text=if(isReady) "Ready" else "Waiting",
                            color=if(isReady) green else orange
                        )
                    }
                    Text(
                        text=if(isReady) "Ready for checkout" else "Not ready yet",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 5.dp)
                    )
                }
                Column(
                    horizontalAlignment=Alignment.End
                ){
                    Text(
                        text="₹${bill.finalAmount}",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="Final",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 2.dp)
                    )
                }
            }
            Spacer(modifier=Modifier.height(12.dp))
            if(userCartItems.isNotEmpty()){
                Box(
                    modifier=Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF7ED), RoundedCornerShape(18.dp))
                        .padding(12.dp)
                ){
                    Column{
                        Text(
                            text="Items added",
                            style=MaterialTheme.typography.bodySmall,
                            color=orange,
                            fontWeight=FontWeight.ExtraBold
                        )
                        Spacer(modifier=Modifier.height(8.dp))
                        userCartItems.forEach{item->
                            BillItemRow(item=item)
                            Spacer(modifier=Modifier.height(8.dp))
                        }
                    }
                }
                Spacer(modifier=Modifier.height(12.dp))
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
                color=Color.White,
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
                    fontWeight=FontWeight.ExtraBold,
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
                    text="₹${item.price} × ${item.quantity}",
                    style=MaterialTheme.typography.bodySmall,
                    color=grayText,
                    modifier=Modifier.padding(top = 4.dp)
                )
            }
            Text(
                text="₹${item.price*item.quantity}",
                style=MaterialTheme.typography.bodyMedium,
                fontWeight=FontWeight.ExtraBold,
                color=darkText,
                modifier=Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun FinalSummaryCard(
    deliveryFee:Int,
    platformFee:Int,
    grandTotal:Int,
    readyCount:Int,
    participantsCount:Int,
    allReady:Boolean,
    isCartLocked:Boolean,
    isCurrentUserHost:Boolean,
    orange:Color,
    darkText:Color,
    grayText:Color,
    onCheckoutClick:()->Unit,
    onLockCartClick:()->Unit,
    onUnlockCartClick:()->Unit
){
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
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text=if(allReady){
                            "Everyone is ready."
                        }else{
                            "$readyCount/$participantsCount users ready"
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
                        ), shape=RoundedCornerShape(16.dp)
                    ){
                        Text(
                            text="Lock Cart & Checkout",
                            fontWeight=FontWeight.Bold
                        )
                    }
                }else if(allReady&&!isCurrentUserHost){
                    Box(
                        modifier=Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFF7ED), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ){
                        Text(
                            text="Everyone is ready. Waiting for host to lock cart.",
                            style=MaterialTheme.typography.bodyMedium,
                            color=darkText,
                            fontWeight=FontWeight.Bold
                        )
                    }
                }else{
                    Box(
                        modifier=Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFF7ED), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ){
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
    }
}

@Composable
fun StatusPill(
    text:String,
    color:Color
){
    Box(
        modifier=Modifier
            .background(
                color=color.copy(alpha = 0.12f),
                shape=RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ){
        Text(
            text=text,
            color=color,
            style=MaterialTheme.typography.labelSmall,
            fontWeight=FontWeight.ExtraBold
        )
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