package com.absar.eatsync.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.absar.eatsync.data.repository.FoodRepository
import com.absar.eatsync.model.food.AddonChoice
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
    val deepOrange=Color(0xFFE95D00)
    val background=Color(0xFFFFF7ED)
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)
    val red=Color(0xFFE53935)
    val green=Color(0xFF48C479)
    val softOrange=Color(0xFFFFE8D2)

    val details=itemDetails

    val selectedAddonTotal=details?.addonGroups?.sumOf { group ->
        val selectedIds=selectedAddonsByGroup[group.groupId] ?: emptySet()

        group.choices
            .filter { choice -> selectedIds.contains(choice.id) }
            .sumOf { choice -> choice.price }
    } ?: 0

    val finalPrice=(details?.basePrice ?: 0) + selectedAddonTotal

    Box(
        modifier=Modifier
            .fillMaxSize()
            .background(background)
    ){
        Box(
            modifier=Modifier
                .fillMaxWidth()
                .height(220.dp)
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
                .padding(top = 28.dp, end = 22.dp)
                .background(Color(0x33FFFFFF), CircleShape)
                .padding(62.dp)
        )

        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ){
            Spacer(modifier=Modifier.height(28.dp))

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.Top
            ){
                Column(
                    modifier=Modifier.weight(1f)
                ){
                    Text(
                        text="Customize item",
                        style=MaterialTheme.typography.headlineMedium,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )

                    Text(
                        text="Choose your size and add-ons",
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
                    Column(
                        horizontalAlignment=Alignment.CenterHorizontally
                    ){
                        Text(
                            text="SESSION",
                            style=MaterialTheme.typography.labelSmall,
                            color=grayText,
                            fontWeight=FontWeight.Bold
                        )

                        Text(
                            text=sessionCode,
                            color=orange,
                            fontWeight=FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(modifier=Modifier.height(18.dp))

            if(isLoading){
                Card(
                    modifier=Modifier.fillMaxWidth(),
                    colors=CardDefaults.cardColors(
                        containerColor=Color.White
                    ),
                    shape=RoundedCornerShape(24.dp),
                    elevation=CardDefaults.cardElevation(
                        defaultElevation=5.dp
                    )
                ){
                    Column(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
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

                Spacer(modifier=Modifier.weight(1f))
            }else{
                LazyColumn(
                    modifier=Modifier.weight(1f)
                ){
                    item{
                        ItemSummaryCard(
                            details=details,
                            itemName=itemName,
                            itemId=itemId,
                            selectedAddonTotal=selectedAddonTotal,
                            finalPrice=finalPrice,
                            orange=orange,
                            deepOrange=deepOrange,
                            darkText=darkText,
                            grayText=grayText,
                            softOrange=softOrange
                        )

                        Spacer(modifier=Modifier.height(16.dp))
                    }

                    if(details == null || (details.variantGroups.isEmpty() && details.addonGroups.isEmpty())){
                        item{
                            Card(
                                modifier=Modifier.fillMaxWidth(),
                                colors=CardDefaults.cardColors(
                                    containerColor=Color.White
                                ),
                                shape=RoundedCornerShape(24.dp),
                                elevation=CardDefaults.cardElevation(
                                    defaultElevation=4.dp
                                )
                            ){
                                Column(
                                    modifier=Modifier.padding(18.dp)
                                ){
                                    Text(
                                        text="Customization details unavailable",
                                        fontWeight=FontWeight.ExtraBold,
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

                    if(details != null && (details.variantGroups.isNotEmpty() || details.addonGroups.isNotEmpty())){
                        item{
                            UnifiedCustomizationCard(
                                details=details,
                                selectedVariants=selectedVariants,
                                selectedAddonsByGroup=selectedAddonsByGroup,
                                orange=orange,
                                darkText=darkText,
                                grayText=grayText,
                                red=red,
                                green=green
                            )

                            Spacer(modifier=Modifier.height(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier=Modifier.height(10.dp))

            BottomAddBar(
                finalPrice=finalPrice,
                itemDetails=itemDetails,
                orange=orange,
                darkText=darkText,
                grayText=grayText,
                onAddClick={
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
                }
            )

            Spacer(modifier=Modifier.height(10.dp))

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

            Spacer(modifier=Modifier.height(14.dp))
        }
    }
}

@Composable
fun ItemSummaryCard(
    details:FoodMenuItemDetails?,
    itemName:String,
    itemId:String,
    selectedAddonTotal:Int,
    finalPrice:Int,
    orange:Color,
    deepOrange:Color,
    darkText:Color,
    grayText:Color,
    softOrange:Color
){
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .shadow(7.dp, RoundedCornerShape(26.dp)),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        shape=RoundedCornerShape(26.dp)
    ){
        Column(
            modifier=Modifier.padding(18.dp)
        ){
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.Top
            ){
                Column(
                    modifier=Modifier.weight(1f)
                ){
                    Text(
                        text=details?.name?.takeIf { it.isNotBlank() } ?: itemName,
                        style=MaterialTheme.typography.titleLarge,
                        fontWeight=FontWeight.ExtraBold,
                        color=darkText
                    )

                    Text(
                        text="Menu item ID: $itemId",
                        color=grayText,
                        style=MaterialTheme.typography.bodySmall,
                        modifier=Modifier.padding(top = 5.dp)
                    )
                }

                Box(
                    modifier=Modifier
                        .background(softOrange, RoundedCornerShape(50.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ){
                    Text(
                        text="MCP details",
                        color=deepOrange,
                        style=MaterialTheme.typography.bodySmall,
                        fontWeight=FontWeight.Bold
                    )
                }
            }

            Spacer(modifier=Modifier.height(14.dp))

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.spacedBy(10.dp)
            ){
                PricePill(
                    title="Base",
                    amount=details?.basePrice ?: 0,
                    modifier=Modifier.weight(1f),
                    color=darkText
                )

                PricePill(
                    title="Add-ons",
                    amount=selectedAddonTotal,
                    modifier=Modifier.weight(1f),
                    color=orange
                )

                PricePill(
                    title="Final",
                    amount=finalPrice,
                    modifier=Modifier.weight(1f),
                    color=orange
                )
            }
        }
    }
}

@Composable
fun UnifiedCustomizationCard(
    details:FoodMenuItemDetails,
    selectedVariants:MutableMap<String,String>,
    selectedAddonsByGroup:MutableMap<String,Set<String>>,
    orange:Color,
    darkText:Color,
    grayText:Color,
    red:Color,
    green:Color
){
    Card(
        modifier=Modifier.fillMaxWidth(),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        shape=RoundedCornerShape(26.dp),
        elevation=CardDefaults.cardElevation(
            defaultElevation=5.dp
        )
    ){
        Column(
            modifier=Modifier.padding(18.dp)
        ){
            Text(
                text="Customize your item",
                style=MaterialTheme.typography.titleMedium,
                fontWeight=FontWeight.ExtraBold,
                color=darkText
            )

            Text(
                text="Size, add-ons and beverages in one place",
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 3.dp)
            )

            Spacer(modifier=Modifier.height(18.dp))

            details.variantGroups.forEach { group ->
                OptionSectionHeader(
                    title=group.name,
                    subtitle="Choose one option",
                    chipText="Required",
                    chipColor=orange
                )

                Spacer(modifier=Modifier.height(10.dp))

                group.options.forEach { option ->
                    val isSelected=selectedVariants[group.groupId]==option.id

                    VariantOptionRow(
                        name=option.name,
                        isSelected=isSelected,
                        inStock=option.inStock,
                        orange=orange,
                        darkText=darkText,
                        grayText=grayText,
                        red=red,
                        onClick={
                            if(option.inStock){
                                selectedVariants[group.groupId]=option.id
                            }
                        }
                    )

                    Spacer(modifier=Modifier.height(8.dp))
                }

                SectionDivider()
            }

            details.addonGroups.forEachIndexed { index, group ->
                val selectedIds=selectedAddonsByGroup[group.groupId] ?: emptySet()
                val isBeverageGroup=group.groupName.contains("beverage", ignoreCase = true)
                        || group.groupName.contains("drink", ignoreCase = true)

                OptionSectionHeader(
                    title=group.groupName,
                    subtitle="Choose up to ${group.maxAddons}",
                    chipText="${selectedIds.size}/${group.maxAddons}",
                    chipColor=if(selectedIds.isNotEmpty()) green else orange
                )

                Spacer(modifier=Modifier.height(10.dp))

                if(isBeverageGroup){
                    BeverageGrid(
                        choices=group.choices,
                        selectedIds=selectedIds,
                        orange=orange,
                        darkText=darkText,
                        grayText=grayText,
                        onChoiceClick={choice->
                            val currentSet=selectedAddonsByGroup[group.groupId] ?: emptySet()

                            if(currentSet.contains(choice.id)){
                                selectedAddonsByGroup[group.groupId]=currentSet - choice.id
                            }else if(currentSet.size < group.maxAddons){
                                selectedAddonsByGroup[group.groupId]=currentSet + choice.id
                            }
                        }
                    )
                }else{
                    group.choices.forEach { choice ->
                        val isChecked=selectedIds.contains(choice.id)

                        AddonOptionRow(
                            name=choice.name,
                            price=choice.price,
                            isChecked=isChecked,
                            orange=orange,
                            darkText=darkText,
                            grayText=grayText,
                            onClick={
                                val currentSet=selectedAddonsByGroup[group.groupId] ?: emptySet()

                                if(currentSet.contains(choice.id)){
                                    selectedAddonsByGroup[group.groupId]=currentSet - choice.id
                                }else if(currentSet.size < group.maxAddons){
                                    selectedAddonsByGroup[group.groupId]=currentSet + choice.id
                                }
                            },
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

                        Spacer(modifier=Modifier.height(8.dp))
                    }
                }

                if(index != details.addonGroups.lastIndex){
                    SectionDivider()
                }
            }
        }
    }
}

@Composable
fun OptionSectionHeader(
    title:String,
    subtitle:String,
    chipText:String,
    chipColor:Color
){
    val darkText=Color(0xFF1C1C1C)
    val grayText=Color(0xFF686B78)

    Row(
        modifier=Modifier.fillMaxWidth(),
        horizontalArrangement=Arrangement.SpaceBetween,
        verticalAlignment=Alignment.CenterVertically
    ){
        Column(
            modifier=Modifier.weight(1f)
        ){
            Text(
                text=title,
                style=MaterialTheme.typography.titleSmall,
                fontWeight=FontWeight.ExtraBold,
                color=darkText
            )

            Text(
                text=subtitle,
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 2.dp)
            )
        }

        SmallChip(
            text=chipText,
            color=chipColor
        )
    }
}

@Composable
fun SectionDivider(){
    Spacer(modifier=Modifier.height(16.dp))

    Box(
        modifier=Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFFF1F1F1))
    )

    Spacer(modifier=Modifier.height(16.dp))
}

@Composable
fun PricePill(
    title:String,
    amount:Int,
    modifier:Modifier,
    color:Color
){
    Column(
        modifier=modifier
            .background(Color(0xFFFFF7ED), RoundedCornerShape(18.dp))
            .padding(vertical = 10.dp),
        horizontalAlignment=Alignment.CenterHorizontally
    ){
        Text(
            text=title,
            color=Color(0xFF686B78),
            style=MaterialTheme.typography.bodySmall,
            fontWeight=FontWeight.SemiBold
        )

        Text(
            text="₹$amount",
            color=color,
            style=MaterialTheme.typography.titleMedium,
            fontWeight=FontWeight.ExtraBold,
            modifier=Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun VariantOptionRow(
    name:String,
    isSelected:Boolean,
    inStock:Boolean,
    orange:Color,
    darkText:Color,
    grayText:Color,
    red:Color,
    onClick:()->Unit
){
    val bgColor=if(isSelected) Color(0xFFFFF1E3) else Color(0xFFFFF7ED)

    Row(
        modifier=Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(18.dp))
            .clickable {
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment=Alignment.CenterVertically
    ){
        RadioButton(
            selected=isSelected,
            onClick=onClick
        )

        Column(
            modifier=Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ){
            Text(
                text=name,
                color=darkText,
                fontWeight=FontWeight.Bold
            )

            Text(
                text=if(inStock) "Available" else "Out of stock",
                color=if(inStock) grayText else red,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 2.dp)
            )
        }

        if(isSelected){
            SmallChip(
                text="Selected",
                color=orange
            )
        }
    }
}

@Composable
fun AddonOptionRow(
    name:String,
    price:Int,
    isChecked:Boolean,
    orange:Color,
    darkText:Color,
    grayText:Color,
    onClick:()->Unit,
    onCheckedChange:(Boolean)->Unit
){
    val bgColor=if(isChecked) Color(0xFFFFF1E3) else Color(0xFFFFF7ED)

    Row(
        modifier=Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(18.dp))
            .clickable {
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment=Alignment.CenterVertically
    ){
        Checkbox(
            checked=isChecked,
            onCheckedChange=onCheckedChange
        )

        Column(
            modifier=Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ){
            Text(
                text=name,
                color=darkText,
                fontWeight=FontWeight.Bold
            )

            Text(
                text="+ ₹$price",
                color=grayText,
                style=MaterialTheme.typography.bodySmall,
                modifier=Modifier.padding(top = 2.dp)
            )
        }

        if(isChecked){
            SmallChip(
                text="Added",
                color=orange
            )
        }
    }
}

@Composable
fun BeverageGrid(
    choices:List<AddonChoice>,
    selectedIds:Set<String>,
    orange:Color,
    darkText:Color,
    grayText:Color,
    onChoiceClick:(AddonChoice)->Unit
){
    val rows=choices.chunked(2)

    Column(
        verticalArrangement=Arrangement.spacedBy(10.dp)
    ){
        rows.forEach { rowChoices ->
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.spacedBy(10.dp)
            ){
                rowChoices.forEach { choice ->
                    BeverageTile(
                        choice=choice,
                        isSelected=selectedIds.contains(choice.id),
                        orange=orange,
                        darkText=darkText,
                        grayText=grayText,
                        modifier=Modifier.weight(1f),
                        onClick={
                            onChoiceClick(choice)
                        }
                    )
                }

                if(rowChoices.size==1){
                    Spacer(modifier=Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun BeverageTile(
    choice:AddonChoice,
    isSelected:Boolean,
    orange:Color,
    darkText:Color,
    grayText:Color,
    modifier:Modifier,
    onClick:()->Unit
){
    val bgColor=if(isSelected) Color(0xFFFFF1E3) else Color(0xFFFFF7ED)

    Column(
        modifier=modifier
            .background(bgColor, RoundedCornerShape(18.dp))
            .clickable {
                onClick()
            }
            .padding(12.dp),
        horizontalAlignment=Alignment.CenterHorizontally
    ){
        Text(
            text="🥤",
            style=MaterialTheme.typography.titleLarge
        )

        Text(
            text=choice.name,
            color=darkText,
            fontWeight=FontWeight.ExtraBold,
            style=MaterialTheme.typography.bodyMedium,
            modifier=Modifier.padding(top = 6.dp)
        )

        Text(
            text="+ ₹${choice.price}",
            color=grayText,
            style=MaterialTheme.typography.bodySmall,
            modifier=Modifier.padding(top = 2.dp)
        )

        if(isSelected){
            SmallChip(
                text="Added",
                color=orange
            )
        }
    }
}

@Composable
fun BottomAddBar(
    finalPrice:Int,
    itemDetails:FoodMenuItemDetails?,
    orange:Color,
    darkText:Color,
    grayText:Color,
    onAddClick:()->Unit
){
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        colors=CardDefaults.cardColors(
            containerColor=Color.White
        ),
        shape=RoundedCornerShape(24.dp)
    ){
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement=Arrangement.SpaceBetween,
            verticalAlignment=Alignment.CenterVertically
        ){
            Column{
                Text(
                    text="Final item price",
                    style=MaterialTheme.typography.bodySmall,
                    color=grayText,
                    fontWeight=FontWeight.SemiBold
                )

                Text(
                    text="₹$finalPrice",
                    style=MaterialTheme.typography.titleLarge,
                    color=darkText,
                    fontWeight=FontWeight.ExtraBold
                )
            }

            Button(
                onClick=onAddClick,
                enabled=itemDetails != null && (itemDetails.basePrice) > 0,
                colors=ButtonDefaults.buttonColors(
                    containerColor=orange
                ),
                shape=RoundedCornerShape(16.dp)
            ){
                Text(
                    text="Add item",
                    fontWeight=FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SmallChip(
    text:String,
    color:Color
){
    Box(
        modifier=Modifier
            .background(
                color=color.copy(alpha = 0.12f),
                shape=RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 9.dp, vertical = 5.dp)
    ){
        Text(
            text=text,
            color=color,
            style=MaterialTheme.typography.labelSmall,
            fontWeight=FontWeight.ExtraBold
        )
    }
}