package com.absar.eatsync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WaitingRoomScreen(
    sessionCode:String,
    isHost:Boolean,
    onSelectRestaurantClick:()->Unit,
    onBackClick:()->Unit
) {
    val dummyParticipants=listOf("Absar", "Lala", "Avro", "Ansh", "Aryan")
    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement=Arrangement.Center,
        horizontalAlignment=Alignment.CenterHorizontally
    ){
        Text(
            text="Waiting Room",
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold
        )
        Text(
            text="Share this code with your friends.",
            modifier=Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
        Card(
            modifier=Modifier.fillMaxWidth()
        ){
            Column(
                modifier=Modifier.padding(20.dp),
                horizontalAlignment=Alignment.CenterHorizontally
            ){
                Text(
                    text="Session Code",
                    style=MaterialTheme.typography.titleMedium
                )
                Text(
                    text=sessionCode,
                    style=MaterialTheme.typography.headlineLarge,
                    fontWeight=FontWeight.Bold,
                    modifier=Modifier.padding(top = 8.dp)
                )
            }
        }
        Spacer(modifier=Modifier.height(20.dp))
        Card(
            modifier=Modifier.fillMaxWidth()
        ){
            Column(
                modifier=Modifier.padding(20.dp)
            ){
                Text(
                    text="Participants",
                    style=MaterialTheme.typography.titleMedium,
                    fontWeight=FontWeight.Bold
                )
                Spacer(modifier=Modifier.height(12.dp))
                dummyParticipants.forEach{name->
                    Row(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(vertical=6.dp),
                        horizontalArrangement=Arrangement.SpaceBetween
                    ){
                        Text(text=name)
                        Text(text="Joined")
                    }
                }
            }
        }
        Spacer(modifier=Modifier.height(20.dp))
        if(isHost){
            Button(
                onClick=onSelectRestaurantClick,
                modifier=Modifier.fillMaxWidth()
            ){
                Text("Select Restaurant")
            }
        }else{
            Text(
                text="Waiting for host to select a restaurant...",
                style=MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier=Modifier.height(16.dp))
        OutlinedButton(
            onClick=onBackClick,
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Back")
        }
    }
}