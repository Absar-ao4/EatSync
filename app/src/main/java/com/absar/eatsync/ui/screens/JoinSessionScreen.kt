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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun JoinSessionScreen(
    onBackClick: () -> Unit,
    onSessionJoined: (String) -> Unit
){
    var userName by remember { mutableStateOf("") }
    var sessionCode by remember { mutableStateOf("") }
    var joinedCode by remember { mutableStateOf<String?>(null) }
    val isJoinEnabled=userName.trim().isNotEmpty() && sessionCode.length == 6
    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement=Arrangement.Center,
        horizontalAlignment=Alignment.CenterHorizontally
    ){
        Text(
            text="Join Group Order",
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold
        )
        Text(
            text="Enter your name and session code.",
            modifier=Modifier.padding(top = 8.dp, bottom = 24.dp)
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
        Spacer(modifier=Modifier.height(16.dp))
        Button(
            onClick={
                joinedCode=sessionCode
                onSessionJoined(sessionCode)
            },
            enabled=isJoinEnabled,
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Join Session")
        }
        if(joinedCode!=null){
            Spacer(modifier=Modifier.height(24.dp))
            Card(
                modifier=Modifier.fillMaxWidth()
            ){
                Column(
                    modifier=Modifier.padding(20.dp),
                    horizontalAlignment=Alignment.CenterHorizontally
                ){
                    Text(
                        text="Joined Session",
                        style=MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text=joinedCode ?: "",
                        style=MaterialTheme.typography.headlineLarge,
                        fontWeight=FontWeight.Bold,
                        modifier=Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text="Firebase validation will come next.",
                        modifier=Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier=Modifier.height(24.dp))
        OutlinedButton(
            onClick= onBackClick,
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Back")
        }
    }
}