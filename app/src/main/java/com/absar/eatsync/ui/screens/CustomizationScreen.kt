package com.absar.eatsync.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomizationScreen(
    sessionCode:String,
    restaurantId:String,
    itemId:String,
    itemName:String,
    onBackClick:()->Unit,
    onAddCustomizedItemClick:()->Unit
){
    val orange=Color(0xFFFC8019)
    val background=Color(0xFFFFF8F1)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)

    Column(
        modifier=Modifier
            .fillMaxSize()
            .background(background)
            .padding(20.dp)
    ){
        Text(
            text="Customize item",
            style=MaterialTheme.typography.headlineMedium,
            fontWeight=FontWeight.Bold,
            color=darkText
        )

        Text(
            text="Session: $sessionCode",
            style=MaterialTheme.typography.bodyMedium,
            color=grayText,
            modifier=Modifier.padding(top = 4.dp)
        )

        Spacer(modifier=Modifier.height(18.dp))

        Card(
            modifier=Modifier.fillMaxWidth(),
            colors=CardDefaults.cardColors(
                containerColor=Color.White
            ),
            elevation=CardDefaults.cardElevation(
                defaultElevation=3.dp
            ),
            shape=RoundedCornerShape(18.dp)
        ){
            Column(
                modifier=Modifier.padding(16.dp)
            ){
                Text(
                    text=itemName,
                    style=MaterialTheme.typography.titleLarge,
                    fontWeight=FontWeight.Bold,
                    color=darkText
                )

                Text(
                    text="Restaurant ID: $restaurantId",
                    color=grayText,
                    modifier=Modifier.padding(top = 6.dp)
                )

                Text(
                    text="Menu item ID: $itemId",
                    color=grayText,
                    modifier=Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier=Modifier.height(16.dp))

        Card(
            modifier=Modifier.fillMaxWidth(),
            colors=CardDefaults.cardColors(
                containerColor=Color.White
            ),
            shape=RoundedCornerShape(18.dp)
        ){
            Column(
                modifier=Modifier.padding(16.dp)
            ){
                Text(
                    text="Variant and add-on selection coming next",
                    fontWeight=FontWeight.Bold,
                    color=orange
                )
            }
        }

        Spacer(modifier=Modifier.weight(1f))

        Button(
            onClick=onAddCustomizedItemClick,
            modifier=Modifier.fillMaxWidth(),
            colors=ButtonDefaults.buttonColors(
                containerColor=orange
            ),
            shape=RoundedCornerShape(14.dp)
        ){
            Text("Add after customization")
        }

        Spacer(modifier=Modifier.height(12.dp))

        OutlinedButton(
            onClick=onBackClick,
            modifier=Modifier.fillMaxWidth(),
            shape=RoundedCornerShape(14.dp)
        ){
            Text(
                text="Back",
                color=orange
            )
        }
    }
}