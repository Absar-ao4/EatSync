package com.absar.eatsync.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.data.repository.FoodRepository
import com.absar.eatsync.model.food.FoodMenuItem
import com.absar.eatsync.model.food.FoodMenuItemDetails

@Composable
fun CustomizationScreen(
    sessionCode:String,
    restaurantId:String,
    itemId:String,
    itemName:String,
    onBackClick:()->Unit,
    onAddCustomizedItemClick:(FoodMenuItem)->Unit
){
    val foodRepository=remember { FoodRepository() }

    var itemDetails by remember { mutableStateOf<FoodMenuItemDetails?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val selectedVariants=remember { mutableStateMapOf<String,String>() }
    val selectedAddonsByGroup=remember { mutableStateMapOf<String,Set<String>>() }

    LaunchedEffect(restaurantId,itemId){
        isLoading=true

        val details=foodRepository.getMenuItemDetails(
            restaurantId=restaurantId,
            itemId=itemId
        )

        itemDetails=details
        selectedVariants.clear()
        selectedAddonsByGroup.clear()

        details.variantGroups.forEach { group ->
            val defaultOption=group.options.firstOrNull { it.defaultSelected }
                ?: group.options.firstOrNull()

            if(defaultOption != null){
                selectedVariants[group.groupId]=defaultOption.id
            }
        }

        isLoading=false
    }

    val orange=Color(0xFFFC8019)
    val background=Color(0xFFFFF8F1)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val red=Color(0xFFE53935)

    val details=itemDetails

    val selectedAddonTotal=details?.addonGroups?.sumOf { group ->
        val selectedIds=selectedAddonsByGroup[group.groupId] ?: emptySet()

        group.choices
            .filter { choice -> selectedIds.contains(choice.id) }
            .sumOf { choice -> choice.price }
    } ?: 0

    val finalPrice=(details?.basePrice ?: 0) + selectedAddonTotal

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

        if(isLoading){
            Card(
                modifier=Modifier.fillMaxWidth(),
                colors=CardDefaults.cardColors(
                    containerColor=Color.White
                ),
                shape=RoundedCornerShape(18.dp)
            ){
                Column(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment=Alignment.CenterHorizontally
                ){
                    CircularProgressIndicator(
                        color=orange
                    )

                    Text(
                        text="Loading customization details...",
                        color=grayText,
                        modifier=Modifier.padding(top = 12.dp)
                    )
                }
            }
        }else{
            LazyColumn(
                modifier=Modifier.weight(1f)
            ){
                item{
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
                                text=details?.name?.takeIf { it.isNotBlank() } ?: itemName,
                                style=MaterialTheme.typography.titleLarge,
                                fontWeight=FontWeight.Bold,
                                color=darkText
                            )

                            Text(
                                text="Base price: ₹${details?.basePrice ?: 0}",
                                color=darkText,
                                fontWeight=FontWeight.Bold,
                                modifier=Modifier.padding(top = 8.dp)
                            )

                            if(selectedAddonTotal > 0){
                                Text(
                                    text="Add-ons total: ₹$selectedAddonTotal",
                                    color=orange,
                                    fontWeight=FontWeight.Bold,
                                    modifier=Modifier.padding(top = 4.dp)
                                )
                            }

                            Text(
                                text="Final price: ₹$finalPrice",
                                color=darkText,
                                fontWeight=FontWeight.Bold,
                                modifier=Modifier.padding(top = 4.dp)
                            )

                            Text(
                                text="Menu item ID: $itemId",
                                color=grayText,
                                modifier=Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier=Modifier.height(16.dp))
                }

                if(details == null || (details.variantGroups.isEmpty() && details.addonGroups.isEmpty())){
                    item{
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
                                    text="Customization details unavailable",
                                    fontWeight=FontWeight.Bold,
                                    color=orange
                                )

                                Text(
                                    text="We have not fetched variant/add-on details for this item yet. Try Hyderabadi Veg Dum Biryani from Biryani Blues for now.",
                                    color=grayText,
                                    modifier=Modifier.padding(top = 8.dp)
                                )
                            }
                        }

                        Spacer(modifier=Modifier.height(16.dp))
                    }
                }

                details?.variantGroups?.forEach { group ->
                    item{
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
                                    text=group.name,
                                    style=MaterialTheme.typography.titleMedium,
                                    fontWeight=FontWeight.Bold,
                                    color=darkText
                                )

                                Text(
                                    text="Choose one",
                                    color=grayText,
                                    modifier=Modifier.padding(top = 4.dp)
                                )

                                Spacer(modifier=Modifier.height(8.dp))

                                group.options.forEach { option ->
                                    Row(
                                        modifier=Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                if(option.inStock){
                                                    selectedVariants[group.groupId]=option.id
                                                }
                                            }
                                            .padding(vertical = 6.dp),
                                        verticalAlignment=Alignment.CenterVertically
                                    ){
                                        RadioButton(
                                            selected=selectedVariants[group.groupId]==option.id,
                                            onClick={
                                                if(option.inStock){
                                                    selectedVariants[group.groupId]=option.id
                                                }
                                            }
                                        )

                                        Column(
                                            modifier=Modifier.padding(start = 8.dp)
                                        ){
                                            Text(
                                                text=option.name,
                                                color=darkText,
                                                fontWeight=FontWeight.Medium
                                            )

                                            if(!option.inStock){
                                                Text(
                                                    text="Out of stock",
                                                    color=red,
                                                    style=MaterialTheme.typography.bodySmall
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier=Modifier.height(16.dp))
                    }
                }

                details?.addonGroups?.forEach { group ->
                    item{
                        val selectedIds=selectedAddonsByGroup[group.groupId] ?: emptySet()

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
                                    text=group.groupName,
                                    style=MaterialTheme.typography.titleMedium,
                                    fontWeight=FontWeight.Bold,
                                    color=darkText
                                )

                                Text(
                                    text="Choose up to ${group.maxAddons}",
                                    color=grayText,
                                    modifier=Modifier.padding(top = 4.dp)
                                )

                                Spacer(modifier=Modifier.height(8.dp))

                                group.choices.forEach { choice ->
                                    val isChecked=selectedIds.contains(choice.id)

                                    Row(
                                        modifier=Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                val currentSet=selectedAddonsByGroup[group.groupId] ?: emptySet()

                                                if(currentSet.contains(choice.id)){
                                                    selectedAddonsByGroup[group.groupId]=currentSet - choice.id
                                                }else if(currentSet.size < group.maxAddons){
                                                    selectedAddonsByGroup[group.groupId]=currentSet + choice.id
                                                }
                                            }
                                            .padding(vertical = 6.dp),
                                        verticalAlignment=Alignment.CenterVertically
                                    ){
                                        Checkbox(
                                            checked=isChecked,
                                            onCheckedChange={checked->
                                                val currentSet=selectedAddonsByGroup[group.groupId] ?: emptySet()

                                                if(checked){
                                                    if(currentSet.size < group.maxAddons){
                                                        selectedAddonsByGroup[group.groupId]=currentSet + choice.id
                                                    }
                                                }else{
                                                    selectedAddonsByGroup[group.groupId]=currentSet - choice.id
                                                }
                                            }
                                        )

                                        Column(
                                            modifier=Modifier.padding(start = 8.dp)
                                        ){
                                            Text(
                                                text=choice.name,
                                                color=darkText,
                                                fontWeight=FontWeight.Medium
                                            )

                                            Text(
                                                text="+ ₹${choice.price}",
                                                color=grayText,
                                                style=MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier=Modifier.height(16.dp))
                    }
                }
            }
        }

        Button(
            onClick={
                val currentDetails=itemDetails

                if(currentDetails != null && currentDetails.basePrice > 0){
                    val selectedVariantNames=currentDetails.variantGroups.mapNotNull { group ->
                        val selectedOptionId=selectedVariants[group.groupId]

                        group.options.firstOrNull {
                            it.id==selectedOptionId
                        }?.name
                    }

                    val selectedAddonNames=currentDetails.addonGroups.flatMap { group ->
                        val selectedIds=selectedAddonsByGroup[group.groupId] ?: emptySet()

                        group.choices.filter { choice ->
                            selectedIds.contains(choice.id)
                        }.map { choice ->
                            choice.name
                        }
                    }

                    val description=buildString {
                        append("Customized item")

                        if(selectedVariantNames.isNotEmpty()){
                            append(" • ")
                            append(selectedVariantNames.joinToString(", "))
                        }

                        if(selectedAddonNames.isNotEmpty()){
                            append(" • Add-ons: ")
                            append(selectedAddonNames.joinToString(", "))
                        }
                    }

                    val selectedVariantIds=currentDetails.variantGroups.mapNotNull { group ->
                        selectedVariants[group.groupId]
                    }

                    val selectedAddonIds=currentDetails.addonGroups.flatMap { group ->
                        selectedAddonsByGroup[group.groupId] ?: emptySet()
                    }

                    val customizationKey=(selectedVariantIds + selectedAddonIds)
                        .sorted()
                        .joinToString("_")

                    val customizedItemId=if(customizationKey.isNotBlank()){
                        "${currentDetails.itemId}_custom_$customizationKey"
                    }else{
                        currentDetails.itemId
                    }

                    val customizedItem=FoodMenuItem(
                        id=customizedItemId,
                        restaurantId=currentDetails.restaurantId,
                        name=currentDetails.name,
                        description=description,
                        price=finalPrice,
                        veg=true,
                        imageUrl="",
                        inStock=true,
                        hasVariants=currentDetails.variantGroups.isNotEmpty(),
                        hasAddons=currentDetails.addonGroups.isNotEmpty()
                    )

                    onAddCustomizedItemClick(customizedItem)
                }
            },
            enabled=itemDetails != null && (itemDetails?.basePrice ?: 0) > 0,
            modifier=Modifier.fillMaxWidth(),
            colors=ButtonDefaults.buttonColors(
                containerColor=orange
            ),
            shape=RoundedCornerShape(14.dp)
        ){
            Text("Add item • ₹$finalPrice")
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