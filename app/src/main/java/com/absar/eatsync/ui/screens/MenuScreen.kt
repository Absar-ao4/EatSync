package com.absar.eatsync.ui.screens

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

data class DummyMenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Int
)

@Composable
fun MenuScreen(
    sessionCode:String,
    restaurantId:String,
    restaurantName:String,
    onAddItemClick:(DummyMenuItem)->Unit,
    onViewCartClick:()->Unit,
    onBackClick:()->Unit
) {
    val menuItems=listOf(
        DummyMenuItem("m1", "Margherita Pizza", "Classic cheese pizza with tomato sauce", 299),
        DummyMenuItem("m2", "Farmhouse Pizza", "Loaded with capsicum, onion, tomato and cheese", 399),
        DummyMenuItem("m3", "Veg Burger", "Crispy veg patty with mayo and lettuce", 149),
        DummyMenuItem("m4", "French Fries", "Crispy salted fries", 129),
        DummyMenuItem("m5", "Cold Coffee", "Chilled coffee with ice cream", 179)
    )
    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text=restaurantName,
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold
        )
        Text(
            text="Session: $sessionCode",
            style=MaterialTheme.typography.bodyMedium,
            modifier=Modifier.padding(top = 4.dp)
        )
        Text(
            text="Restaurant ID: $restaurantId",
            style=MaterialTheme.typography.bodySmall,
            modifier=Modifier.padding(top = 2.dp, bottom = 20.dp)
        )
        LazyColumn(
            modifier=Modifier.weight(1f)
        ){
            items(menuItems){item->
                MenuItemCard(
                    item=item,
                    onAddClick={
                        onAddItemClick(item)
                    }
                )
                Spacer(modifier=Modifier.height(12.dp))
            }
        }
        Button(
            onClick=onViewCartClick,
            modifier=Modifier.fillMaxWidth()
        ) {
            Text("View Shared Cart")
        }
        Spacer(modifier=Modifier.height(12.dp))
        OutlinedButton(
            onClick=onBackClick,
            modifier=Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Composable
fun MenuItemCard(
    item:DummyMenuItem,
    onAddClick:()->Unit
) {
    Card(
        modifier=Modifier.fillMaxWidth()
    ) {
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment=Alignment.CenterVertically
        ) {
            Column(
                modifier=Modifier.weight(1f)
            ) {
                Text(
                    text=item.name,
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.Bold
                )
                Text(
                    text=item.description,
                    style=MaterialTheme.typography.bodyMedium,
                    modifier=Modifier.padding(top = 4.dp)
                )
                Text(
                    text="₹${item.price}",
                    style=MaterialTheme.typography.titleSmall,
                    fontWeight=FontWeight.Bold,
                    modifier=Modifier.padding(top = 8.dp)
                )
            }
            Button(
                onClick=onAddClick
            ) {
                Text("Add")
            }
        }
    }
}