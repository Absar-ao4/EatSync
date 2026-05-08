package com.absar.eatsync.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.absar.eatsync.firebase.FirebaseSessionManager
import com.absar.eatsync.model.CartItem
import com.absar.eatsync.ui.screens.DummyMenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val firebaseSessionManager = FirebaseSessionManager()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private var currentSessionCode: String? = null
    private var currentUserName: String = "Absar"

    fun getCurrentUserName():String{
        return currentUserName
    }

    fun startCartSync(
        sessionCode: String,
        userName: String
    ) {
        currentSessionCode = sessionCode
        currentUserName = userName

        viewModelScope.launch {
            firebaseSessionManager.observeCartItems(sessionCode)
                .collect { items ->
                    _cartItems.value = items
                }
        }
    }

    fun addItem(menuItem: DummyMenuItem) {
        val sessionCode = currentSessionCode

        if (sessionCode == null) {
            Log.e("EatSyncFirebase", "addItem failed: sessionCode is null")
            return
        }

        val currentItems = _cartItems.value.toMutableList()

        val existingItem = currentItems.firstOrNull {
            it.id == menuItem.id && it.addedByName == currentUserName
        }

        val updatedCartItem = if (existingItem != null) {
            existingItem.copy(
                quantity = existingItem.quantity + 1
            )
        } else {
            CartItem(
                id = "${menuItem.id}_$currentUserName",
                name = menuItem.name,
                price = menuItem.price,
                quantity = 1,
                addedByName = currentUserName
            )
        }

        viewModelScope.launch {
            firebaseSessionManager.addOrUpdateCartItem(
                sessionCode = sessionCode,
                cartItem = updatedCartItem
            )
        }
    }

    fun increaseQuantity(itemId: String) {
        val sessionCode = currentSessionCode ?: return

        val item = _cartItems.value.firstOrNull {
            it.id == itemId
        } ?: return

        viewModelScope.launch {
            firebaseSessionManager.addOrUpdateCartItem(
                sessionCode = sessionCode,
                cartItem = item.copy(quantity = item.quantity + 1)
            )
        }
    }

    fun decreaseQuantity(itemId: String) {
        val sessionCode = currentSessionCode ?: return

        val item = _cartItems.value.firstOrNull {
            it.id == itemId
        } ?: return

        viewModelScope.launch {
            if (item.quantity > 1) {
                firebaseSessionManager.addOrUpdateCartItem(
                    sessionCode = sessionCode,
                    cartItem = item.copy(quantity = item.quantity - 1)
                )
            } else {
                firebaseSessionManager.removeCartItem(
                    sessionCode = sessionCode,
                    itemId = itemId
                )
            }
        }
    }

    fun removeItem(itemId: String) {
        val sessionCode = currentSessionCode ?: return

        viewModelScope.launch {
            firebaseSessionManager.removeCartItem(
                sessionCode = sessionCode,
                itemId = itemId
            )
        }
    }

    fun clearCart() {
        val sessionCode = currentSessionCode ?: return

        viewModelScope.launch {
            firebaseSessionManager.clearCart(sessionCode)
        }
    }
}