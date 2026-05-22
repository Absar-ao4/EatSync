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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import java.util.Locale

@Composable
fun JoinSessionScreen(
    onBackClick: () -> Unit,
    onSessionJoined: (String, String) -> Unit
){
    var userName by remember { mutableStateOf("") }
    var sessionCode by remember { mutableStateOf("") }
    var joinedCode by remember { mutableStateOf<String?>(null) }

    val isJoinEnabled=userName.trim().isNotEmpty() && sessionCode.length == 6

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
                            background
                        )
                    )
                )
        )
        Box(
            modifier=Modifier
                .align(Alignment.TopEnd)
                .padding(top = 42.dp, end = 22.dp)
                .background(Color(0x33FFFFFF), CircleShape)
                .padding(70.dp)
        )
        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ){
            Spacer(modifier=Modifier.height(30.dp))
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.Top
            ){
                Column(
                    modifier=Modifier.weight(1f)
                ){
                    Text(
                        text="Join order",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )

                    Text(
                        text="Enter the code shared by your host",
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
                    Text(
                        text="JOIN",
                        color=orange,
                        fontWeight=FontWeight.ExtraBold
                    )
                }
            }
            Spacer(modifier=Modifier.height(26.dp))
            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(28.dp)),
                shape=RoundedCornerShape(28.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ){
                Column(
                    modifier=Modifier.padding(20.dp)
                ){
                    Box(
                        modifier=Modifier
                            .background(softOrange, RoundedCornerShape(50.dp))
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ){
                        Text(
                            text="Join your friend’s cart",
                            color=deepOrange,
                            style=MaterialTheme.typography.bodySmall,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier=Modifier.height(16.dp))
                    Text(
                        text="Add your details",
                        style=MaterialTheme.typography.titleLarge,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text="Your name will appear in the waiting room, shared cart, and bill split.",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 7.dp)
                    )
                    Spacer(modifier=Modifier.height(18.dp))
                    OutlinedTextField(
                        value=userName,
                        onValueChange={userName=it},
                        label={Text("Your name")},
                        singleLine=true,
                        modifier=Modifier.fillMaxWidth()
                    )
                    Spacer(modifier=Modifier.height(12.dp))
                    OutlinedTextField(
                        value=sessionCode,
                        onValueChange={ value ->
                            sessionCode=value
                                .uppercase(Locale.getDefault())
                                .filter { it.isLetterOrDigit() }
                                .take(6)
                        },
                        label={Text("Session code")},
                        singleLine=true,
                        modifier=Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(top = 7.dp),
                        horizontalArrangement=Arrangement.SpaceBetween,
                        verticalAlignment=Alignment.CenterVertically
                    ){
                        Text(
                            text="${sessionCode.length}/6 characters",
                            style=MaterialTheme.typography.bodySmall,
                            color=grayText
                        )
                        Text(
                            text=if(isJoinEnabled) "Ready to join" else "Code required",
                            style=MaterialTheme.typography.bodySmall,
                            color=if(isJoinEnabled) green else grayText,
                            fontWeight=FontWeight.Bold
                        )
                    }
                    Spacer(modifier=Modifier.height(18.dp))
                    Button(
                        onClick={
                            joinedCode=sessionCode
                            onSessionJoined(sessionCode,userName.trim())
                        },
                        enabled=isJoinEnabled,
                        modifier=Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors=ButtonDefaults.buttonColors(
                            containerColor=orange
                        ),
                        shape=RoundedCornerShape(16.dp)
                    ){
                        Text(
                            text="Join Session",
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                }
            }
            if(joinedCode!=null){
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
                    Row(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement=Arrangement.SpaceBetween,
                        verticalAlignment=Alignment.CenterVertically
                    ){
                        Column(
                            modifier=Modifier.weight(1f)
                        ){
                            Text(
                                text="Joined session",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.ExtraBold,
                                color=darkText
                            )
                            Text(
                                text="Waiting for host updates",
                                style=MaterialTheme.typography.bodySmall,
                                color=grayText,
                                modifier=Modifier.padding(top = 3.dp)
                            )
                        }
                        Box(
                            modifier=Modifier
                                .background(Color(0xFFE9F8EF), RoundedCornerShape(16.dp))
                                .padding(horizontal = 13.dp, vertical = 9.dp)
                        ){
                            Text(
                                text=joinedCode ?: "",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.ExtraBold,
                                color=green
                            )
                        }
                    }
                }
            }
            Spacer(modifier=Modifier.weight(1f))
            Card(
                modifier=Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(24.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ){
                Column(
                    modifier=Modifier.padding(16.dp)
                ){
                    JoinMiniInfoRow(
                        text="You can add your own food items after joining",
                        orange=orange,
                        darkText=darkText
                    )
                    Spacer(modifier=Modifier.height(8.dp))
                    JoinMiniInfoRow(
                        text="Your bill is calculated separately from others",
                        orange=orange,
                        darkText=darkText
                    )
                }
            }
            Spacer(modifier=Modifier.height(12.dp))
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
fun JoinMiniInfoRow(
    text:String,
    orange:Color,
    darkText:Color
){
    Row(
        modifier=Modifier.fillMaxWidth(),
        verticalAlignment=Alignment.CenterVertically
    ){
        Box(
            modifier=Modifier
                .background(Color(0xFFFFE8D2), CircleShape)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ){
            Text(
                text="✓",
                color=orange,
                fontWeight=FontWeight.ExtraBold
            )
        }
        Text(
            text=text,
            color=darkText,
            style=MaterialTheme.typography.bodySmall,
            fontWeight=FontWeight.SemiBold,
            modifier=Modifier.padding(start = 10.dp)
        )
    }
}