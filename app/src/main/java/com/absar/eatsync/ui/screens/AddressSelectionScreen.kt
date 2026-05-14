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
import com.absar.eatsync.model.food.FoodAddress

@Composable
fun AddressSelectionScreen(
    sessionCode:String,
    onAddressSelected:(FoodAddress)->Unit,
    onBackClick:()->Unit
){
    val foodRepository=remember { FoodRepository() }
    val addresses=foodRepository.getAddresses()

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
                        text="Select address",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )

                    Text(
                        text="Choose delivery location",
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
                        text="Swiggy address step",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )
                    Text(
                        text="For now this uses dummy address data. Later this will come from Swiggy get_addresses.",
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
fun AddressCard(
    address:FoodAddress,
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
                    .padding(horizontal = 13.dp, vertical = 18.dp)
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
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Text(
                        text=address.addressTag.ifEmpty { "Saved Address" },
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
                            text=address.addressCategory.ifEmpty { "ADDRESS" },
                            color=green,
                            fontWeight=FontWeight.Bold,
                            style=MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Text(
                    text=address.addressLine,
                    style=MaterialTheme.typography.bodyMedium,
                    color=grayText,
                    modifier=Modifier.padding(top = 4.dp)
                )
                Row(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
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
                        fontWeight=FontWeight.Bold
                    )
                }
            }
        }
    }
}