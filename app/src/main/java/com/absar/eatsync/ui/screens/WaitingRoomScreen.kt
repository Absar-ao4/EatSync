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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.absar.eatsync.model.Participant
import androidx.compose.ui.unit.dp
import com.absar.eatsync.model.SelectedRestaurant

@Composable
fun WaitingRoomScreen(
    sessionCode:String,
    isHost:Boolean,
    participants:List<Participant>,
    selectedRestaurant:SelectedRestaurant?,
    isCartLocked: Boolean,
    onSelectRestaurantClick:()->Unit,
    onOpenMenuClick:(SelectedRestaurant)->Unit,
    onChangeRestaurantClick:()->Unit,
    onBackClick:()->Unit
){
    var showChangeRestaurantDialog by remember { mutableStateOf(false) }

    val orange=Color(0xFFFC8019)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)

    if(showChangeRestaurantDialog){
        AlertDialog(
            onDismissRequest={
                showChangeRestaurantDialog=false
            },
            title={
                Text(
                    text="Change restaurant?",
                    fontWeight=FontWeight.Bold
                )
            },
            text={
                Text(
                    text="Changing restaurant will clear the shared cart and reset everyone's ready status. This cannot be undone."
                )
            },
            confirmButton={
                Button(
                    onClick={
                        showChangeRestaurantDialog=false
                        onChangeRestaurantClick()
                    },
                    colors=ButtonDefaults.buttonColors(
                        containerColor=orange
                    )
                ){
                    Text("Clear & Change")
                }
            },
            dismissButton={
                OutlinedButton(
                    onClick={
                        showChangeRestaurantDialog=false
                    }
                ){
                    Text(
                        text="Cancel",
                        color=orange
                    )
                }
            }
        )
    }
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
                        text="Waiting room",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )
                    Text(
                        text=if(isHost){
                            "Share the code and manage the order"
                        }else{
                            "You are inside the group order"
                        },
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top=4.dp)
                    )
                }
                Box(
                    modifier=Modifier
                        .background(Color.White,RoundedCornerShape(18.dp))
                        .padding(horizontal=12.dp,vertical=8.dp)
                ){
                    Text(
                        text=if(isHost) "HOST"
                                else "GUEST",
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
                        elevation=7.dp,
                        shape=RoundedCornerShape(24.dp)
                    ),
                shape=RoundedCornerShape(24.dp),
                colors=CardDefaults.cardColors(containerColor=Color.White)
            ){
                Column(
                    modifier=Modifier.padding(20.dp),
                    horizontalAlignment=Alignment.CenterHorizontally
                ){
                    Text(
                        text="Session Code",
                        style=MaterialTheme.typography.titleMedium,
                        color=grayText
                    )
                    Text(
                        text=sessionCode,
                        style=MaterialTheme.typography.headlineLarge,
                        fontWeight=FontWeight.ExtraBold,
                        color=orange,
                        modifier=Modifier.padding(top = 6.dp)
                    )
                    Text(
                        text="${participants.size} people joined",
                        style=MaterialTheme.typography.bodyMedium,
                        color=grayText,
                        modifier=Modifier.padding(top = 6.dp)
                    )
                }
            }
            Spacer(modifier=Modifier.height(16.dp))
            Card(
                modifier=Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(22.dp),
                colors=CardDefaults.cardColors(containerColor=Color.White)
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
                                text="Participants",
                                style=MaterialTheme.typography.titleMedium,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )
                            Text(
                                text="Live members in this session",
                                style=MaterialTheme.typography.bodySmall,
                                color=grayText,
                                modifier=Modifier.padding(top = 3.dp)
                            )
                        }
                        Text(
                            text="${participants.size}",
                            style=MaterialTheme.typography.titleLarge,
                            fontWeight=FontWeight.ExtraBold,
                            color=orange
                        )
                    }
                    Spacer(modifier=Modifier.height(14.dp))
                    if(participants.isEmpty()){
                        Text(
                            text="No one has joined yet.",
                            color=grayText
                        )
                    }else{
                        participants.forEach{participant->
                            ParticipantRow(
                                participant=participant
                            )
                            Spacer(modifier=Modifier.height(10.dp))
                        }
                    }
                }
            }
            Spacer(modifier=Modifier.height(16.dp))
            if(selectedRestaurant != null) {
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
                        Row(
                            modifier=Modifier.fillMaxWidth(),
                            horizontalArrangement=Arrangement.SpaceBetween,
                            verticalAlignment=Alignment.CenterVertically
                        ){
                            Column(
                                modifier=Modifier.weight(1f)
                            ){
                                Text(
                                    text="Selected Restaurant",
                                    style=MaterialTheme.typography.titleMedium,
                                    fontWeight=FontWeight.Bold,
                                    color=darkText
                                )
                                Text(
                                    text=selectedRestaurant.name,
                                    style=MaterialTheme.typography.bodyLarge,
                                    fontWeight=FontWeight.Bold,
                                    color=darkText,
                                    modifier=Modifier.padding(top = 5.dp)
                                )
                                Text(
                                    text=if(isCartLocked){
                                        "Cart is locked. Unlock before changing restaurant."
                                    }else{
                                        "Everyone can now open the same menu."
                                    },
                                    style=MaterialTheme.typography.bodySmall,
                                    color=grayText,
                                    modifier=Modifier.padding(top = 4.dp)
                                )
                            }
                            Box(
                                modifier=Modifier
                                    .background(
                                        if(isCartLocked) Color(0xFFFFE8D2) else Color(0xFFE9F8EF),
                                        RoundedCornerShape(50.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ){
                                Text(
                                    text=if(isCartLocked) "LOCKED" else "READY",
                                    color=if(isCartLocked) orange else green,
                                    fontWeight=FontWeight.Bold,
                                    style=MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        Spacer(modifier=Modifier.height(14.dp))
                        Button(
                            onClick = {
                                onOpenMenuClick(selectedRestaurant)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors=ButtonDefaults.buttonColors(
                                containerColor=orange
                            ),
                            shape=RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text="Open Menu",
                                fontWeight=FontWeight.Bold
                            )
                        }
                        if(isHost){
                            Spacer(modifier=Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = {
                                    if(!isCartLocked){
                                        showChangeRestaurantDialog=true
                                    }
                                },
                                enabled=!isCartLocked,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape=RoundedCornerShape(16.dp)
                            ){
                                Text(
                                    text=if(isCartLocked){
                                        "Unlock Cart to Change Restaurant"
                                    }else{
                                        "Change Restaurant"
                                    },
                                    color=orange,
                                    fontWeight=FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }else{
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
                            text=if(isHost){
                                "Restaurant not selected"
                            }else{
                                "Waiting for host"
                            },
                            style=MaterialTheme.typography.titleMedium,
                            fontWeight=FontWeight.Bold,
                            color=darkText
                        )
                        Text(
                            text=if(isHost){
                                "Choose where this group order should be placed from."
                            }else{
                                "The host will select a restaurant soon. Stay here."
                            },
                            style=MaterialTheme.typography.bodyMedium,
                            color=grayText,
                            modifier=Modifier.padding(top = 6.dp,bottom = 14.dp)
                        )
                        if(isHost){
                            Button(
                                onClick = onSelectRestaurantClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                colors=ButtonDefaults.buttonColors(
                                    containerColor=orange
                                ),
                                shape=RoundedCornerShape(16.dp)
                            ){
                                Text(
                                    text="Select Restaurant",
                                    fontWeight=FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier=Modifier.weight(1f))
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
fun ParticipantRow(
    participant:Participant
){
    val orange=Color(0xFFFC8019)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val green=Color(0xFF48C479)
    Row(
        modifier=Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFAF5), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalArrangement=Arrangement.SpaceBetween,
        verticalAlignment=Alignment.CenterVertically
    ){
        Column{
            Text(
                text=participant.name,
                color=darkText,
                fontWeight=FontWeight.Bold,
                style=MaterialTheme.typography.bodyMedium
            )
            Text(
                text=if(participant.host){
                    "Session host"
                }else{
                    "Joined member"
                },
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 2.dp)
            )
        }
        Box(
            modifier=Modifier
                .background(
                    if(participant.host) Color(0xFFFFE8D2)
                                else Color(0xFFE9F8EF),
                    RoundedCornerShape(50.dp)
                )
                .padding(horizontal=10.dp,vertical=5.dp)
        ){
            Text(
                text=if(participant.host){
                    "HOST"
                }else{
                    "JOINED"
                },
                color=if(participant.host) orange
                        else green,
                fontWeight=FontWeight.Bold,
                style=MaterialTheme.typography.bodySmall
            )
        }
    }
}