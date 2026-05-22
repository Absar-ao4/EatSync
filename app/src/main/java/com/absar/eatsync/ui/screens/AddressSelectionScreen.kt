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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.data.repository.FoodRepository
import com.absar.eatsync.model.food.FoodAddress

@Composable
fun AddressSelectionScreen(
    sessionCode:String,
    onAddressSelected:(FoodAddress)->Unit,
    onBackClick:()->Unit
){
    val foodRepository=remember { FoodRepository() }
    var addresses by remember { mutableStateOf<List<FoodAddress>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit){
        addresses=foodRepository.getAddresses()
        isLoading=false
    }

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
                .height(240.dp)
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
                .padding(top = 38.dp, end = 22.dp)
                .background(Color(0x33FFFFFF), CircleShape)
                .padding(70.dp)
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
                        text="Select address",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="Choose delivery location for this group order",
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
            Spacer(modifier=Modifier.height(20.dp))
            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation=8.dp,
                        shape=RoundedCornerShape(28.dp)
                    ),
                shape=RoundedCornerShape(28.dp),
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
                                text="Delivery address",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.ExtraBold,
                                color=darkText
                            )
                            Text(
                                text=if(isLoading){
                                    "Loading saved addresses from EatSync backend..."
                                }else{
                                    "${addresses.size} saved address option(s) available"
                                },
                                style=MaterialTheme.typography.bodySmall,
                                color=grayText,
                                modifier=Modifier.padding(top = 4.dp)
                            )
                        }
                        Box(
                            modifier=Modifier
                                .background(softOrange, RoundedCornerShape(50.dp))
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
                            text="This address will be used to fetch nearby restaurants for the session.",
                            color=grayText,
                            style=MaterialTheme.typography.bodySmall,
                            fontWeight=FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.height(14.dp))
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
                            text="Loading addresses...",
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
                    items(addresses){address->
                        AddressCard(
                            address=address,
                            onClick={
                                onAddressSelected(address)
                            }
                        )
                        Spacer(modifier=Modifier.height(12.dp))
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
            Spacer(modifier=Modifier.height(16.dp))
        }
    }
}

@Composable
fun AddressCard(
    address:FoodAddress,
    onClick:()->Unit
){
    val orange=Color(0xFFFC8019)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)
    val softOrange=Color(0xFFFFE8D2)

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
                            .background(softOrange, RoundedCornerShape(18.dp))
                            .padding(horizontal = 13.dp, vertical = 12.dp)
                    ){
                        Text(
                            text=address.addressTag.take(1).ifEmpty { "A" },
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
                        Text(
                            text=address.addressTag.ifEmpty { "Saved Address" },
                            style=MaterialTheme.typography.titleMedium,
                            fontWeight=FontWeight.ExtraBold,
                            color=darkText
                        )
                        Text(
                            text=address.addressLine,
                            style=MaterialTheme.typography.bodyMedium,
                            color=grayText,
                            modifier=Modifier.padding(top = 5.dp)
                        )
                    }
                }
                Box(
                    modifier=Modifier
                        .background(Color(0xFFE9F8EF), RoundedCornerShape(50.dp))
                        .padding(horizontal = 9.dp, vertical = 5.dp)
                ){
                    Text(
                        text=address.addressCategory.ifEmpty { "ADDRESS" },
                        color=green,
                        fontWeight=FontWeight.Bold,
                        style=MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier=Modifier.height(12.dp))
            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF7ED), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically
            ){
                Text(
                    text=address.phoneNumber,
                    style=MaterialTheme.typography.bodySmall,
                    color=grayText,
                    fontWeight=FontWeight.SemiBold
                )
                Text(
                    text="Use address",
                    style=MaterialTheme.typography.bodySmall,
                    color=orange,
                    fontWeight=FontWeight.ExtraBold
                )
            }
        }
    }
}