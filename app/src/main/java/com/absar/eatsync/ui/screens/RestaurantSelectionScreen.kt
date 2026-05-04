package com.absar.eatsync.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class DummyRestaurant(
    val id: String,
    val name: String,
    val cuisine: String,
    val rating: String,
    val deliveryTime: String
)
@Composable
fun RestaurantSelectionScreen(
    sessionCode: String,
    onRestaurantSelected: (DummyRestaurant) -> Unit,
    onBackClick: () -> Unit
){
    val restaurants = listOf(
        DummyRestaurant("r1", "Pizza Hut", "Pizza, Fast Food", "4.2", "30-35 min"),
        DummyRestaurant("r2", "Burger King", "Burgers, Beverages", "4.1", "25-30 min"),
        DummyRestaurant("r3", "KFC", "Chicken, Fast Food", "4.0", "35-40 min"),
        DummyRestaurant("r4", "La Pino'z Pizza", "Pizza, Italian", "4.3", "30-35 min"),
        DummyRestaurant("r5", "Wow! Momo", "Momos, Tibetan", "4.4", "20-25 min")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ){
        Text(
            text = "Select Restaurant",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Session: $sessionCode",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ){
            items(restaurants){ restaurant ->
                RestaurantCard(
                    restaurant=restaurant,
                    onClick={
                        onRestaurantSelected(restaurant)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Back")
        }
    }
}
@Composable
fun RestaurantCard(
    restaurant: DummyRestaurant,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = restaurant.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = restaurant.cuisine,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "⭐ ${restaurant.rating} • ${restaurant.deliveryTime}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}