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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.model.CartItem

@Composable
fun SharedCartScreen(
    sessionCode: String,
    cartItems: List<CartItem>,
    currentUserName: String,
    onIncreaseQuantity: (String) -> Unit,
    onDecreaseQuantity: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
    onContinueToBillSplitClick: () -> Unit,
    onBackClick: () -> Unit
){
    val itemTotal=cartItems.sumOf { it.price * it.quantity }
    val deliveryFee =
        if (cartItems.isEmpty())
            0 else 40
    val platformFee =
        if (cartItems.isEmpty())
            0 else 10
    val grandTotal=itemTotal+deliveryFee+platformFee
    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text="Shared Cart",
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold
        )
        Text(
            text="Session: $sessionCode",
            style=MaterialTheme.typography.bodyMedium,
            modifier=Modifier.padding(top=4.dp,bottom=20.dp)
        )
        if(cartItems.isEmpty()){
            Card(
                modifier=Modifier.fillMaxWidth()
            ){
                Text(
                    text="Cart is empty. Go back and add some items.",
                    modifier=Modifier.padding(20.dp)
                )
            }
        }
        else{
            LazyColumn(
                modifier=Modifier.weight(1f)
            ){
                items(cartItems){ item->
                    CartItemCard(
                        item = item,
                        canEdit = item.addedByName == currentUserName,
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
        Spacer(modifier=Modifier.height(20.dp))
        Card(
            modifier=Modifier.fillMaxWidth()
        ){
            Column(
                modifier=Modifier.padding(20.dp)
            ){
                Text(
                    text="Bill Summary",
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.Bold
                )
                Spacer(modifier=Modifier.height(12.dp))
                BillRow(label="Item Total",amount=itemTotal)
                BillRow(label="Delivery Fee",amount=deliveryFee)
                BillRow(label="Platform Fee",amount=platformFee)
                Spacer(modifier=Modifier.height(12.dp))
                BillRow(label="Grand Total",amount=grandTotal,isBold=true)
            }
        }
        Spacer(modifier=Modifier.height(24.dp))
        Button(
            onClick=onContinueToBillSplitClick,
            enabled=cartItems.isNotEmpty(),
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Continue to Bill Split")
        }
        Spacer(modifier=Modifier.height(12.dp))
        OutlinedButton(
            onClick=onBackClick,
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Back")
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    canEdit: Boolean,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    onRemoveClick: () -> Unit
){
    Card(
        modifier=Modifier.fillMaxWidth()
    ){
        Column(
            modifier=Modifier.padding(16.dp)
        ){
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween
            ){
                Column(
                    modifier=Modifier.weight(1f)
                ){
                    Text(
                        text=item.name,
                        style=MaterialTheme.typography.bodyLarge,
                        fontWeight=FontWeight.Bold
                    )
                    Text(
                        text="Added by ${item.addedByName}",
                        style=MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text="₹${item.price} each",
                        style=MaterialTheme.typography.bodySmall,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
                Text(
                    text="₹${item.price * item.quantity}",
                    style=MaterialTheme.typography.bodyLarge,
                    fontWeight=FontWeight.Bold
                )
            }
            Spacer(modifier=Modifier.height(12.dp))
            if(canEdit){
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=androidx.compose.ui.Alignment.CenterVertically
                ){
                    Row(
                        verticalAlignment=androidx.compose.ui.Alignment.CenterVertically
                    ){
                        OutlinedButton(
                            onClick=onDecreaseClick
                        ){
                            Text("-")
                        }
                        Text(
                            text="Qty: ${item.quantity}",
                            modifier=Modifier.padding(horizontal = 12.dp),
                            fontWeight=FontWeight.Bold
                        )
                        OutlinedButton(
                            onClick=onIncreaseClick
                        ){
                            Text("+")
                        }
                    }
                    OutlinedButton(
                        onClick=onRemoveClick
                    ){
                        Text("Remove")
                    }
                }
            }
            else{
                Text(
                    text="Only ${item.addedByName} can edit this item",
                    style=MaterialTheme.typography.bodySmall,
                    modifier=Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun BillRow(
    label:String,
    amount:Int,
    isBold:Boolean=false
){
    Row(
        modifier=Modifier
            .fillMaxWidth()
            .padding(vertical=4.dp),
        horizontalArrangement=Arrangement.SpaceBetween
    ){
        Text(
            text=label,
            fontWeight=
                if(isBold)
                    FontWeight.Bold
                else
                    FontWeight.Normal
        )
        Text(
            text="₹$amount",
            fontWeight=
                if(isBold)
                    FontWeight.Bold
                else
                    FontWeight.Normal
        )
    }
}