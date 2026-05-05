package com.absar.eatsync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun CreateSessionScreen(
    onBackClick: () -> Unit,
    onSessionCreated: (String) -> Unit
){
    var hostName by remember {mutableStateOf("")}
    var sessionCode by remember {mutableStateOf<String?>(null)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement=Arrangement.Center,
        horizontalAlignment=Alignment.CenterHorizontally
    ){
        Text(
            text="Create Group Order",
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold
        )
        Text(
            text="Start a session and invite your friends.",
            modifier=Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
        OutlinedTextField(
            value=hostName,
            onValueChange={hostName=it},
            label={Text("Your name")},
            singleLine=true,
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(modifier=Modifier.height(16.dp))
        Button(
            onClick={
                val code=generateSessionCode()
                sessionCode=code
                onSessionCreated(code)
            },
            enabled=hostName.trim().isNotEmpty(),
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Create Session")
        }
        if(sessionCode!=null){
            Spacer(modifier=Modifier.height(24.dp))
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
                        text=sessionCode ?: "",
                        style=MaterialTheme.typography.headlineLarge,
                        fontWeight=FontWeight.Bold,
                        modifier=Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text="Share this code with your friends.",
                        modifier=Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier=Modifier.height(24.dp))
        OutlinedButton(
            onClick=onBackClick,
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Back")
        }
    }
}

private fun generateSessionCode(): String{
    val chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..6)
        .map { chars[Random.nextInt(chars.length)] }
        .joinToString("")
}