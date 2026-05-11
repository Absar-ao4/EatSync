package com.absar.eatsync.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.data.repository.FoodRepository
import com.absar.eatsync.model.food.FoodRestaurant

@Composable
fun RestaurantSelectionScreen(
    sessionCode:String,
    onRestaurantSelected:(FoodRestaurant)->Unit,
    onBackClick:()->Unit
){
    val foodRepository=remember { FoodRepository() }
    val restaurants=foodRepository.getRestaurants()

    val orange=Color(0xFFFC8019)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)

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
                        text="Pick restaurant",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )

                    Text(
                        text="Choose where the group order starts",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
                Box(
                    modifier=Modifier
                        .background(Color.White, RoundedCornerShape(18.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    Text(
                        text=sessionCode,
                        color=orange,
                        fontWeight=FontWeight.ExtraBold
                    )
                }
            }
            Spacer(modifier=Modifier.height(18.dp))
            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation=6.dp,
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
                    Text(
                        text="Available near you",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )
                    Text(
                        text="Restaurant data is structured like Swiggy search results. Menu data is dummy until menu tools are available.",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }
            }
            Spacer(modifier=Modifier.height(14.dp))
            LazyColumn(
                modifier=Modifier.weight(1f)
            ){
                items(restaurants){restaurant->
                    RestaurantCard(
                        restaurant=restaurant,
                        onClick={
                            onRestaurantSelected(restaurant)
                        }
                    )

                    Spacer(modifier=Modifier.height(12.dp))
                }
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
fun RestaurantCard(
    restaurant:FoodRestaurant,
    onClick:()->Unit
){
    val orange=Color(0xFFFC8019)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape=RoundedCornerShape(22.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=3.dp
        )
    ){
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment=Alignment.CenterVertically
        ){
            Box(
                modifier=Modifier
                    .background(Color(0xFFFFE8D2), RoundedCornerShape(18.dp))
                    .padding(horizontal = 14.dp, vertical = 18.dp)
            ){
                Text(
                    text=restaurant.name.take(1),
                    color=orange,
                    fontWeight=FontWeight.ExtraBold,
                    style=MaterialTheme.typography.titleLarge
                )
            }
            Column(
                modifier=Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ){
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Text(
                        text=restaurant.name,
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )

                    Box(
                        modifier=Modifier
                            .background(Color(0xFFE9F8EF), RoundedCornerShape(50.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ){
                        Text(
                            text="★ ${restaurant.rating}",
                            color=green,
                            fontWeight=FontWeight.Bold,
                            style=MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Text(
                    text=restaurant.cuisine,
                    style=MaterialTheme.typography.bodyMedium,
                    color=grayText,
                    modifier=Modifier.padding(top = 4.dp)
                )
                Text(
                    text=restaurant.areaName,
                    style=MaterialTheme.typography.bodySmall,
                    color=grayText,
                    modifier=Modifier.padding(top = 3.dp)
                )
                Row(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Text(
                        text="${restaurant.deliveryTime} • ${restaurant.costForTwo}",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        fontWeight=FontWeight.SemiBold
                    )
                    Text(
                        text="Tap to select",
                        style=MaterialTheme.typography.bodySmall,
                        color=orange,
                        fontWeight=FontWeight.Bold
                    )
                }
            }
        }
    }
}