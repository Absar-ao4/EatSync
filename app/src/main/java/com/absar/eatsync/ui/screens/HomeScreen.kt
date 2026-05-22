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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.ui.components.EatSyncLogo

@Composable
fun HomeScreen(
    onCreateSessionClick: () -> Unit,
    onJoinSessionClick: () -> Unit
){
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
                .height(260.dp)
                .background(
                    Brush.verticalGradient(
                        colors=listOf(
                            Color(0xFFFFD2A1),
                            Color(0xFFFFE8D2),
                            background
                        )
                    )
                )
        )

        Box(
            modifier=Modifier
                .align(Alignment.TopEnd)
                .padding(top = 46.dp, end = 18.dp)
                .background(Color(0x44FFFFFF), CircleShape)
                .padding(62.dp)
        )

        Box(
            modifier=Modifier
                .align(Alignment.TopStart)
                .padding(top = 190.dp, start = 18.dp)
                .background(Color(0x33FC8019), CircleShape)
                .padding(28.dp)
        )

        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ){
            Spacer(modifier=Modifier.height(24.dp))

            HomeTopBar(
                orange=orange,
                green=green,
                darkText=darkText,
                grayText=grayText
            )

            Spacer(modifier=Modifier.height(12.dp))

            LazyColumn(
                modifier=Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                item{
                    Text(
                        text="Order food\nwith your group,\nwithout chaos.",
                        style=MaterialTheme.typography.headlineLarge,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )

                    Text(
                        text="Create one shared cart, let friends add customized items, then split the final bill automatically.",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier=Modifier.height(14.dp))

                    HomeLiveCartMock(
                        orange=orange,
                        deepOrange=deepOrange,
                        green=green,
                        darkText=darkText,
                        grayText=grayText,
                        softOrange=softOrange
                    )

                    Spacer(modifier=Modifier.height(12.dp))

                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement=Arrangement.spacedBy(8.dp)
                    ){
                        HomeMiniFeaturePill(
                            title="Live cart",
                            subtitle="Realtime",
                            emoji="🛒",
                            modifier=Modifier.weight(1f)
                        )

                        HomeMiniFeaturePill(
                            title="Add-ons",
                            subtitle="Custom",
                            emoji="🍽️",
                            modifier=Modifier.weight(1f)
                        )

                        HomeMiniFeaturePill(
                            title="Bill split",
                            subtitle="Fair",
                            emoji="💸",
                            modifier=Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier=Modifier.height(18.dp))

                    HomeQuickFlowCard(
                        orange=orange,
                        darkText=darkText,
                        grayText=grayText
                    )

                    Spacer(modifier=Modifier.height(16.dp))
                }
            }

            HomeBottomActions(
                orange=orange,
                darkText=darkText,
                grayText=grayText,
                onCreateSessionClick=onCreateSessionClick,
                onJoinSessionClick=onJoinSessionClick
            )

            Spacer(modifier=Modifier.height(14.dp))
        }
    }
}

@Composable
fun HomeTopBar(
    orange:Color,
    green:Color,
    darkText:Color,
    grayText:Color
){
    Row(
        modifier=Modifier.fillMaxWidth(),
        horizontalArrangement=Arrangement.SpaceBetween,
        verticalAlignment=Alignment.CenterVertically
    ){
        Row(
            verticalAlignment=Alignment.CenterVertically
        ){
            EatSyncLogo(
                size = 54.dp,
                cornerRadius = 18.dp
            )

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
                .shadow(3.dp, RoundedCornerShape(50.dp))
                .background(Color.White, RoundedCornerShape(50.dp))
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
}

@Composable
fun HomeLiveCartMock(
    orange:Color,
    deepOrange:Color,
    green:Color,
    darkText:Color,
    grayText:Color,
    softOrange:Color
){
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .shadow(
                elevation=7.dp,
                shape=RoundedCornerShape(26.dp)
            ),
        shape=RoundedCornerShape(26.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        )
    ){
        Column(
            modifier=Modifier.padding(15.dp)
        ){
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.Top
            ){
                Column{
                    Text(
                        text="Live group cart",
                        color=darkText,
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.ExtraBold
                    )

                    Text(
                        text="2 friends ordering together",
                        color=grayText,
                        style=MaterialTheme.typography.bodySmall,
                        modifier=Modifier.padding(top = 3.dp)
                    )
                }

                Box(
                    modifier=Modifier
                        .background(Color(0xFFE9F8EF), RoundedCornerShape(50.dp))
                        .padding(horizontal = 9.dp, vertical = 5.dp)
                ){
                    Text(
                        text="SYNCED",
                        color=green,
                        style=MaterialTheme.typography.labelSmall,
                        fontWeight=FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier=Modifier.height(12.dp))

            HomeCartMiniRow(
                name="Hyderabadi Veg Dum Biryani",
                subtitle="Absar • Add-ons selected",
                price="₹423",
                orange=orange,
                darkText=darkText,
                grayText=grayText,
                softOrange=softOrange
            )

            Spacer(modifier=Modifier.height(8.dp))

            HomeCartMiniRow(
                name="Coke",
                subtitle="Tenz • 1 item",
                price="₹55",
                orange=orange,
                darkText=darkText,
                grayText=grayText,
                softOrange=softOrange
            )

            Spacer(modifier=Modifier.height(12.dp))

            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF7ED), RoundedCornerShape(16.dp))
                    .padding(11.dp),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically
            ){
                Column{
                    Text(
                        text="Estimated total",
                        color=grayText,
                        style=MaterialTheme.typography.bodySmall,
                        fontWeight=FontWeight.SemiBold
                    )

                    Text(
                        text="Ready for bill split",
                        color=deepOrange,
                        style=MaterialTheme.typography.bodySmall,
                        fontWeight=FontWeight.ExtraBold,
                        modifier=Modifier.padding(top = 2.dp)
                    )
                }

                Text(
                    text="₹528",
                    color=darkText,
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun HomeCartMiniRow(
    name:String,
    subtitle:String,
    price:String,
    orange:Color,
    darkText:Color,
    grayText:Color,
    softOrange:Color
){
    Row(
        modifier=Modifier.fillMaxWidth(),
        horizontalArrangement=Arrangement.SpaceBetween,
        verticalAlignment=Alignment.CenterVertically
    ){
        Row(
            modifier=Modifier.weight(1f),
            verticalAlignment=Alignment.CenterVertically
        ){
            Box(
                modifier=Modifier
                    .background(softOrange, RoundedCornerShape(14.dp))
                    .padding(horizontal = 9.dp, vertical = 7.dp)
            ){
                Text(
                    text="ITEM",
                    color=orange,
                    style=MaterialTheme.typography.labelSmall,
                    fontWeight=FontWeight.ExtraBold
                )
            }

            Column(
                modifier=Modifier.padding(start = 10.dp)
            ){
                Text(
                    text=name,
                    color=darkText,
                    style=MaterialTheme.typography.bodyMedium,
                    fontWeight=FontWeight.ExtraBold,
                    maxLines=1
                )

                Text(
                    text=subtitle,
                    color=grayText,
                    style=MaterialTheme.typography.bodySmall,
                    modifier=Modifier.padding(top = 2.dp),
                    maxLines=1
                )
            }
        }

        Text(
            text=price,
            color=darkText,
            fontWeight=FontWeight.ExtraBold,
            modifier=Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun HomeMiniFeaturePill(
    title:String,
    subtitle:String,
    emoji:String,
    modifier:Modifier
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)

    Card(
        modifier=modifier,
        shape=RoundedCornerShape(20.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=3.dp
        )
    ){
        Column(
            modifier=Modifier.padding(10.dp)
        ){
            Text(
                text=emoji,
                style=MaterialTheme.typography.titleMedium
            )

            Text(
                text=title,
                color=darkText,
                fontWeight=FontWeight.ExtraBold,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 5.dp),
                maxLines=1
            )

            Text(
                text=subtitle,
                color=grayText,
                style=MaterialTheme.typography.labelSmall,
                modifier=Modifier.padding(top = 2.dp),
                maxLines=1
            )
        }
    }
}

@Composable
fun HomeQuickFlowCard(
    orange:Color,
    darkText:Color,
    grayText:Color
){
    Card(
        modifier=Modifier.fillMaxWidth(),
        shape=RoundedCornerShape(22.dp),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        elevation=CardDefaults.cardElevation(
            defaultElevation=3.dp
        )
    ){
        Column(
            modifier=Modifier.padding(14.dp)
        ){
            Text(
                text="How it works",
                color=darkText,
                style=MaterialTheme.typography.titleSmall,
                fontWeight=FontWeight.ExtraBold
            )

            Text(
                text="Host creates a session → friends join → everyone adds food → bill splits automatically.",
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 5.dp)
            )

            Spacer(modifier=Modifier.height(12.dp))

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween
            ){
                HomeFlowDot("1", "Host", orange)
                HomeFlowDot("2", "Join", orange)
                HomeFlowDot("3", "Add", orange)
                HomeFlowDot("4", "Split", orange)
            }
        }
    }
}

@Composable
fun HomeFlowDot(
    number:String,
    label:String,
    orange:Color
){
    Column(
        horizontalAlignment=Alignment.CenterHorizontally
    ){
        Box(
            modifier=Modifier
                .background(Color(0xFFFFE8D2), CircleShape)
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ){
            Text(
                text=number,
                color=orange,
                fontWeight=FontWeight.ExtraBold,
                style=MaterialTheme.typography.bodySmall
            )
        }

        Text(
            text=label,
            color=Color(0xFF686B78),
            style=MaterialTheme.typography.labelSmall,
            fontWeight=FontWeight.SemiBold,
            modifier=Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun HomeBottomActions(
    orange:Color,
    darkText:Color,
    grayText:Color,
    onCreateSessionClick: () -> Unit,
    onJoinSessionClick: () -> Unit
){
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
            modifier=Modifier.padding(15.dp)
        ){
            Text(
                text="Start ordering",
                color=darkText,
                style=MaterialTheme.typography.titleMedium,
                fontWeight=FontWeight.ExtraBold
            )

            Text(
                text="Host a new cart or join your friend’s session.",
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 3.dp)
            )

            Spacer(modifier=Modifier.height(12.dp))

            Button(
                onClick=onCreateSessionClick,
                modifier=Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors=ButtonDefaults.buttonColors(
                    containerColor=orange
                ),
                shape=RoundedCornerShape(16.dp)
            ){
                Text(
                    text="Host Group Order",
                    fontWeight=FontWeight.ExtraBold
                )
            }

            Spacer(modifier=Modifier.height(9.dp))

            OutlinedButton(
                onClick=onJoinSessionClick,
                modifier=Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape=RoundedCornerShape(16.dp),
                colors=ButtonDefaults.outlinedButtonColors(
                    containerColor=Color.White
                )
            ){
                Text(
                    text="Join with Code",
                    color=orange,
                    fontWeight=FontWeight.ExtraBold
                )
            }
        }
    }
}