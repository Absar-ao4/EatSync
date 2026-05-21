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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.absar.eatsync.data.repository.FoodRepository
import com.absar.eatsync.model.food.FoodRestaurant

@Composable
fun RestaurantSelectionScreen(
    sessionCode:String,
    onRestaurantSelected:(FoodRestaurant)->Unit,
    onBackClick:()->Unit
){
    val foodRepository=remember { FoodRepository() }
    var restaurants by remember { mutableStateOf<List<FoodRestaurant>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit){
        restaurants=foodRepository.getRestaurants()
        isLoading=false
    }

    val orange=Color(0xFFFC8019)
    val deepOrange=Color(0xFFE95D00)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val lightOrange=Color(0xFFFFE8D2)

    Box(
        modifier=Modifier
            .fillMaxSize()
            .background(background)
    ){
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(
                    brush=Brush.verticalGradient(
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
                .size(120.dp)
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
                        text="Pick restaurant",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )

                    Text(
                        text="Choose where your group order starts",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 4.dp)
                    )
                }

                Surface(
                    shape=RoundedCornerShape(18.dp),
                    color=Color.White,
                    shadowElevation=3.dp
                ){
                    Column(
                        modifier=Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        Text(
                            text="SESSION",
                            style=MaterialTheme.typography.labelSmall,
                            color=grayText,
                            fontWeight=FontWeight.Bold
                        )

                        Text(
                            text=sessionCode,
                            color=orange,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(modifier=Modifier.height(18.dp))

            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation=8.dp,
                        shape=RoundedCornerShape(26.dp)
                    ),
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
                        verticalAlignment=Alignment.CenterVertically
                    ){
                        Column(
                            modifier=Modifier.weight(1f)
                        ){
                            Text(
                                text="Restaurants near your address",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )

                            Text(
                                text=if(isLoading){
                                    "Fetching restaurants from EatSync backend..."
                                }else{
                                    "${restaurants.size} restaurants available for this session"
                                },
                                style=MaterialTheme.typography.bodySmall,
                                color=grayText,
                                modifier=Modifier.padding(top = 4.dp)
                            )
                        }

                        Box(
                            modifier=Modifier
                                .background(lightOrange, RoundedCornerShape(50.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ){
                            Text(
                                text="MCP data",
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
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ){
                        Text(
                            text="Search result: biryani near KIIT / Patia",
                            color=grayText,
                            style=MaterialTheme.typography.bodySmall,
                            fontWeight=FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.height(16.dp))
            if(isLoading){
                Card(
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(24.dp),
                    colors=CardDefaults.cardColors(
                        containerColor=Color.White
                    )
                ){
                    Column(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        CircularProgressIndicator(
                            color=orange
                        )
                        Text(
                            text="Loading restaurants...",
                            color=grayText,
                            modifier=Modifier.padding(top = 12.dp)
                        )
                    }
                }
                Spacer(modifier=Modifier.weight(1f))
            }else{
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
                        Spacer(modifier=Modifier.height(14.dp))
                    }
                }
            }
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
fun RestaurantCard(
    restaurant:FoodRestaurant,
    onClick:()->Unit
){
    val orange=Color(0xFFFC8019)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)
    val lightOrange=Color(0xFFFFE8D2)
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape=RoundedCornerShape(26.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=4.dp
        )
    ){
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment=Alignment.CenterVertically
        ){
            Box(
                modifier=Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(lightOrange),
                contentAlignment=Alignment.Center
            ){
                if(restaurant.imageUrl.isNotBlank()){
                    AsyncImage(
                        model=restaurant.imageUrl,
                        contentDescription=restaurant.name,
                        contentScale=ContentScale.Crop,
                        modifier=Modifier.fillMaxSize()
                    )
                }else{
                    Text(
                        text=restaurant.name.take(1),
                        color=orange,
                        fontWeight=FontWeight.ExtraBold,
                        style=MaterialTheme.typography.headlineMedium
                    )
                }
            }
            Column(
                modifier=Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ){
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.Top
                ){
                    Text(
                        text=restaurant.name,
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText,
                        modifier=Modifier.weight(1f)
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
                    modifier=Modifier.padding(top = 5.dp),
                    maxLines=2
                )
                Text(
                    text=restaurant.areaName,
                    style=MaterialTheme.typography.bodySmall,
                    color=grayText,
                    modifier=Modifier.padding(top = 3.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Column{
                        Text(
                            text=restaurant.deliveryTime,
                            style=MaterialTheme.typography.bodySmall,
                            color=darkText,
                            fontWeight=FontWeight.Bold
                        )
                        Text(
                            text=restaurant.costForTwo,
                            style=MaterialTheme.typography.bodySmall,
                            color=grayText,
                            modifier=Modifier.padding(top = 2.dp)
                        )
                    }
                    Box(
                        modifier=Modifier
                            .background(lightOrange, RoundedCornerShape(50.dp))
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ){
                        Text(
                            text="Select",
                            style=MaterialTheme.typography.bodySmall,
                            color=orange,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}