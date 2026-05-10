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
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val softOrange=Color(0xFFFFE4C7)
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
                .background(softOrange, CircleShape)
                .padding(64.dp)
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

            Spacer(modifier=Modifier.height(22.dp))

            Card(
                modifier=Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(22.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ){
                Column(
                    modifier=Modifier.padding(18.dp)
                ){
                    Text(
                        text="What happens next?",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )

                    Spacer(modifier=Modifier.height(14.dp))

                    JoinInfoRow(
                        title="Live waiting room",
                        subtitle="See everyone joining the session in real time."
                    )

                    Spacer(modifier=Modifier.height(10.dp))

                    JoinInfoRow(
                        title="Shared cart",
                        subtitle="Add your items while others add theirs."
                    )

                    Spacer(modifier=Modifier.height(10.dp))

                    JoinInfoRow(
                        title="Bill split",
                        subtitle="Pay only for your items plus shared charges."
                    )
                }
            }

            Spacer(modifier=Modifier.height(16.dp))

            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation=7.dp,
                        shape=RoundedCornerShape(24.dp)
                    ),
                shape=RoundedCornerShape(24.dp),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ){
                Column(
                    modifier=Modifier.padding(20.dp)
                ){
                    Text(
                        text="Your details",
                        style=MaterialTheme.typography.titleMedium,
                        fontWeight=FontWeight.Bold,
                        color=darkText
                    )

                    Text(
                        text="This name will appear in the waiting room, cart and bill split.",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 6.dp,bottom = 16.dp)
                    )

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

                    Text(
                        text="${sessionCode.length}/6 characters",
                        style=MaterialTheme.typography.bodySmall,
                        color=grayText,
                        modifier=Modifier.padding(top = 6.dp)
                    )

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
                            fontWeight=FontWeight.Bold
                        )
                    }
                }
            }


            if(joinedCode!=null){
                Spacer(modifier=Modifier.height(16.dp))

                Card(
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(22.dp),
                    colors=CardDefaults.cardColors(
                        containerColor=Color.White
                    )
                ){
                    Row(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement=Arrangement.SpaceBetween,
                        verticalAlignment=Alignment.CenterVertically
                    ){
                        Column{
                            Text(
                                text="Joined Session",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )

                            Text(
                                text="Waiting for host updates",
                                style=MaterialTheme.typography.bodySmall,
                                color=grayText,
                                modifier=Modifier.padding(top = 3.dp)
                            )
                        }

                        Text(
                            text=joinedCode ?: "",
                            style=MaterialTheme.typography.titleLarge,
                            fontWeight=FontWeight.ExtraBold,
                            color=green
                        )
                    }
                }
            }

            Spacer(modifier=Modifier.weight(1f))

            OutlinedButton(
                onClick= onBackClick,
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
fun JoinInfoRow(
    title:String,
    subtitle:String
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val orange=Color(0xFFFC8019)

    Row(
        modifier=Modifier.fillMaxWidth(),
        verticalAlignment=Alignment.CenterVertically
    ){
        Box(
            modifier=Modifier
                .background(Color(0xFFFFE8D2), CircleShape)
                .padding(7.dp)
        ){
            Text(
                text="•",
                color=orange,
                fontWeight=FontWeight.ExtraBold
            )
        }

        Column(
            modifier=Modifier.padding(start = 12.dp)
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
                modifier=Modifier.padding(top = 2.dp)
            )
        }
    }
}