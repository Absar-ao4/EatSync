package com.absar.eatsync.viewmodel

import androidx.lifecycle.ViewModel
import com.absar.eatsync.model.CartItem
import com.absar.eatsync.ui.screens.DummyMenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel:ViewModel(){

    private val _cartItems=MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems:StateFlow<List<CartItem>> =_cartItems.asStateFlow()

    fun addItem(menuItem:DummyMenuItem){
        val currentItems=_cartItems.value.toMutableList()
        val existingItemIndex=currentItems.indexOfFirst{
            it.id==menuItem.id
        }
        if(existingItemIndex!=-1){
            val existingItem=currentItems[existingItemIndex]
            currentItems[existingItemIndex]=existingItem.copy(
                quantity=existingItem.quantity+1
            )
        }else{
            currentItems.add(
                CartItem(
                    id=menuItem.id,
                    name=menuItem.name,
                    price=menuItem.price,
                    quantity=1,
                    addedByName="Absar"
                )
            )
        }
        _cartItems.value=currentItems
    }

    fun increaseQuantity(itemId: String){
        val updatedItems=_cartItems.value.map {item->
            if(item.id==itemId){
                item.copy(quantity=item.quantity+1)
            }else{
                item
            }
        }
        _cartItems.value=updatedItems
    }
    fun decreaseQuantity(itemId: String){
        val currentItems=_cartItems.value.toMutableList()
        val itemIndex=currentItems.indexOfFirst {
            it.id==itemId
        }
        if(itemIndex==-1)
            return
        val item=currentItems[itemIndex]
        if(item.quantity>1){
            currentItems[itemIndex]=item.copy(
                quantity=item.quantity-1
            )
        }else{
            currentItems.removeAt(itemIndex)
        }
        _cartItems.value=currentItems
    }
    fun removeItem(itemId:String){
        _cartItems.value=_cartItems.value.filter{
            it.id!=itemId
        }
    }
    fun clearCart(){
        _cartItems.value=emptyList()
    }
}