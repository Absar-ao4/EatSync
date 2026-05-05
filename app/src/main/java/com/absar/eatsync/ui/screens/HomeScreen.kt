package com.absar.eatsync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onCreateSessionClick: () -> Unit,
    onJoinSessionClick: () -> Unit
){
    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement=Arrangement.Center,
        horizontalAlignment=Alignment.CenterHorizontally
    ) {
        Text(
            text="EatSync",
            style=MaterialTheme.typography.headlineLarge,
            fontWeight=FontWeight.Bold
        )
        Text(
            text="Order food together. Split smarter.",
            style=MaterialTheme.typography.bodyLarge,
            modifier=Modifier.padding(top = 8.dp, bottom = 32.dp)
        )
        Button(
            onClick=onCreateSessionClick,
            modifier=Modifier.fillMaxWidth()
        ) {
            Text(text="Create Group Order")
        }
        OutlinedButton(
            onClick=onJoinSessionClick,
            modifier=Modifier
                .fillMaxWidth()
                .padding(top=12.dp)
        ) {
            Text(text="Join Group Order")
        }
    }
}