package com.absar.eatsync.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.absar.eatsync.R

@Composable
fun EatSyncLogo(
    size: Dp = 52.dp,
    cornerRadius: Dp = 18.dp
){
    ElevatedCard(
        modifier = Modifier.size(size),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFFC8019)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ){
        Box(
            modifier = Modifier
                .size(size)
                .background(Color(0xFFFC8019))
        ){
            Image(
                painter = painterResource(id = R.drawable.eatsync_splash_logo),
                contentDescription = "EatSync logo",
                modifier = Modifier.size(size),
                contentScale = ContentScale.Crop
            )
        }
    }
}