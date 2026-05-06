package com.absar.eatsync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.model.CartItem
import com.absar.eatsync.model.UserBill
import com.absar.eatsync.model.Participant

@Composable
fun BillSplitScreen(
    sessionCode: String,
    cartItems: List<CartItem>,
    participants: List<Participant>,
    onToggleReady: (String) -> Unit,
    onBackClick: () -> Unit
){
    val deliveryFee=if(cartItems.isEmpty()) 0
                    else 40
    val platformFee=if(cartItems.isEmpty()) 0
                    else 10
    val totalSharedCharges=deliveryFee+platformFee
    val sharedChargePerUser=if(participants.isNotEmpty()){
        totalSharedCharges/participants.size
    }else{
        0
    }
    val userBills = participants.map { participant ->
        val itemTotal = cartItems
            .filter { it.addedByName == participant.name }
            .sumOf { it.price * it.quantity }
        UserBill(
            userName = participant.name,
            itemTotal = itemTotal,
            sharedCharges = sharedChargePerUser,
            finalAmount = itemTotal + sharedChargePerUser
        )
    }
    val grandTotal=userBills.sumOf{it.finalAmount}
    val readyCount=participants.count{it.isReady}
    val allReady=participants.isNotEmpty()&&readyCount==participants.size
    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(24.dp)
    ){
        Text(
            text="Bill Split",
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold
        )
        Text(
            text="Session: $sessionCode",
            style=MaterialTheme.typography.bodyMedium,
            modifier=Modifier.padding(top=4.dp,bottom=20.dp)
        )
        Card(
            modifier=Modifier.fillMaxWidth()
        ){
            Column(
                modifier=Modifier.padding(20.dp)
            ){
                Text(
                    text="Split Rule",
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.Bold
                )
                Text(
                    text="Food items are paid by the person who added them. Delivery and platform fees are split equally.",
                    style=MaterialTheme.typography.bodyMedium,
                    modifier=Modifier.padding(top = 8.dp)
                )
            }
        }
        Spacer(modifier=Modifier.height(16.dp))
        LazyColumn(
            modifier=Modifier.weight(1f)
        ){
            items(userBills){bill->
                val participant=participants.firstOrNull{it.name == bill.userName}
                UserBillCard(
                    bill=bill,
                    isReady=participant?.isReady==true,
                    onReadyClick={
                        onToggleReady(bill.userName)
                    }
                )
                Spacer(modifier=Modifier.height(12.dp))
            }
        }
        Card(
            modifier=Modifier.fillMaxWidth()
        ){
            Column(
                modifier=Modifier.padding(20.dp)
            ){
                BillSplitRow(label="Delivery Fee",amount=deliveryFee)
                BillSplitRow(label="Platform Fee",amount=platformFee)
                BillSplitRow(label="Grand Total",amount=grandTotal,isBold=true)
                Spacer(modifier=Modifier.height(8.dp))
                Text(
                    text=if(allReady){
                        "Everyone is ready. Host can proceed to checkout."
                    }else{
                        "$readyCount/${participants.size} users ready"
                    },
                    style=MaterialTheme.typography.bodyMedium,
                    fontWeight=FontWeight.Bold
                )
            }
        }
        Spacer(modifier=Modifier.height(16.dp))
        OutlinedButton(
            onClick=onBackClick,
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Back")
        }
    }
}

@Composable
fun UserBillCard(
    bill:UserBill,
    isReady:Boolean,
    onReadyClick:()->Unit
){
    Card(
        modifier=Modifier.fillMaxWidth()
    ){
        Column(
            modifier=Modifier.padding(16.dp)
        ){
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically
            ){
                Text(
                    text=bill.userName,
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.Bold
                )
                Text(
                    text="₹${bill.finalAmount}",
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.Bold
                )
            }
            Spacer(modifier=Modifier.height(8.dp))
            BillSplitRow(label="Item total",amount=bill.itemTotal)
            BillSplitRow(label="Shared charges",amount=bill.sharedCharges)
            Spacer(modifier=Modifier.height(12.dp))
            Button(
                onClick=onReadyClick,
                modifier=Modifier.fillMaxWidth()
            ){
                Text(
                    text=if(isReady) "Ready ✓"
                            else "Mark Ready"
                )
            }
        }
    }
}

@Composable
fun BillSplitRow(
    label:String,
    amount:Int,
    isBold:Boolean=false
){
    Row(
        modifier=Modifier
            .fillMaxWidth()
            .padding(vertical=3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text=label,
            fontWeight=if(isBold) FontWeight.Bold
                        else FontWeight.Normal
        )
        Text(
            text="₹$amount",
            fontWeight=if(isBold) FontWeight.Bold
                        else FontWeight.Normal
        )
    }
}