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

@Composable
fun HomeScreen(
    onCreateSessionClick: () -> Unit,
    onJoinSessionClick: () -> Unit
){
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
                .padding(68.dp)
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
                Row(
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Box(
                        modifier=Modifier
                            .background(orange, RoundedCornerShape(14.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ){
                        Text(
                            text="ES",
                            color=Color.White,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                    Column(
                        modifier=Modifier.padding(start = 10.dp)
                    ){
                        Text(
                            text="EatSync",
                            style=MaterialTheme.typography.titleLarge,
                            fontWeight=FontWeight.ExtraBold,
                            color=darkText
                        )
                        Text(
                            text="Group food ordering",
                            style=MaterialTheme.typography.bodySmall,
                            color=grayText
                        )
                    }
                }
                Box(
                    modifier=Modifier
                        .background(Color.White, RoundedCornerShape(18.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
                    Text(
                        text="LIVE",
                        color=green,
                        fontWeight=FontWeight.ExtraBold,
                        style=MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier=Modifier.height(24.dp))
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.spacedBy(10.dp)
            ){
                HomeFeatureCard(
                    title="Live Cart",
                    subtitle="Everyone adds together",
                    modifier=Modifier.weight(1f)
                )
                HomeFeatureCard(
                    title="Bill Split",
                    subtitle="Auto split per user",
                    modifier=Modifier.weight(1f)
                )
            }
            Spacer(modifier=Modifier.height(10.dp))
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.spacedBy(10.dp)
            ){
                HomeFeatureCard(
                    title="Ready Flow",
                    subtitle="Lock when all agree",
                    modifier=Modifier.weight(1f)
                )
                HomeFeatureCard(
                    title="Host Checkout",
                    subtitle="One final order",
                    modifier=Modifier.weight(1f)
                )
            }
            Spacer(modifier=Modifier.height(16.dp))
            Card(
                modifier=Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(24.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                ),
                elevation=CardDefaults.cardElevation(
                    defaultElevation=4.dp
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
                                text="Sample shared cart",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )
                            Text(
                                text="How your order looks live",
                                style=MaterialTheme.typography.bodySmall,
                                color=grayText,
                                modifier=Modifier.padding(top = 3.dp)
                            )
                        }
                        Text(
                            text="₹478",
                            color=darkText,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier=Modifier.height(14.dp))
                    HomeCartPreviewRow(
                        name="Margherita Pizza",
                        addedBy="Absar",
                        price="₹299"
                    )
                    Spacer(modifier=Modifier.height(10.dp))
                    HomeCartPreviewRow(
                        name="Cold Coffee",
                        addedBy="Tenz",
                        price="₹179"
                    )
                }
            }
            Spacer(modifier=Modifier.height(16.dp))
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
                    modifier=Modifier.padding(22.dp)
                ){
                    Text(
                        text="Hungry together?",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="Create one shared food cart with friends, split the bill, and stop calculating who owes what.",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 8.dp)
                    )
                    Spacer(modifier=Modifier.height(18.dp))
                    Button(
                        onClick=onCreateSessionClick,
                        modifier=Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors=ButtonDefaults.buttonColors(
                            containerColor=orange
                        ),
                        shape=RoundedCornerShape(16.dp)
                    ){
                        Text(
                            text="Start Group Order",
                            fontWeight=FontWeight.Bold
                        )
                    }
                    Spacer(modifier=Modifier.height(10.dp))
                    OutlinedButton(
                        onClick=onJoinSessionClick,
                        modifier=Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape=RoundedCornerShape(16.dp)
                    ){
                        Text(
                            text="Join with Code",
                            color=orange,
                            fontWeight=FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeFeatureCard(
    title:String,
    subtitle:String,
    modifier:Modifier
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    Card(
        modifier=modifier,
        shape=RoundedCornerShape(20.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        )
    ){
        Column(
            modifier=Modifier.padding(14.dp)
        ){
            Text(
                text=title,
                color=darkText,
                fontWeight=FontWeight.Bold,
                style=MaterialTheme.typography.bodyMedium
            )
            Text(
                text=subtitle,
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun HomeCartPreviewRow(
    name:String,
    addedBy:String,
    price:String
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val orange=Color(0xFFFC8019)
    Row(
        modifier=Modifier.fillMaxWidth(),
        horizontalArrangement=Arrangement.SpaceBetween,
        verticalAlignment=Alignment.CenterVertically
    ){
        Row(
            verticalAlignment=Alignment.CenterVertically
        ){
            Box(
                modifier=Modifier
                    .background(Color(0xFFFFE8D2), RoundedCornerShape(12.dp))
                    .padding(horizontal = 9.dp, vertical = 6.dp)
            ){
                Text(
                    text="VEG",
                    color=orange,
                    fontWeight=FontWeight.Bold,
                    style=MaterialTheme.typography.bodySmall
                )
            }

            Column(
                modifier=Modifier.padding(start = 10.dp)
            ){
                Text(
                    text=name,
                    color=darkText,
                    fontWeight=FontWeight.Bold,
                    style=MaterialTheme.typography.bodyMedium
                )
                Text(
                    text="Added by $addedBy",
                    color=grayText,
                    style=MaterialTheme.typography.bodySmall,
                    modifier=Modifier.padding(top = 2.dp)
                )
            }
        }
        Text(
            text=price,
            color=darkText,
            fontWeight=FontWeight.Bold
        )
    }
}