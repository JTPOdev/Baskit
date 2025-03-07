package com.example.splashscreenbaskit.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.splashscreenbaskit.Carts.CartItem

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    fun addToCart(item: CartItem) {
        if (item.quantity <= 0) return
        val existingItemIndex = _cartItems.indexOfFirst { it.name == item.name && it.weight == item.weight }
        if (existingItemIndex != -1) {
            val existingItem = _cartItems[existingItemIndex]
            _cartItems[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + item.quantity)
        } else {
            _cartItems.add(item.copy())
        }
    }

    fun removeFromCart(item: CartItem) {
        _cartItems.removeAll { it.id == item.id }
    }

    fun increaseQuantity(item: CartItem) {
        val index = _cartItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            _cartItems[index] = item.copy(quantity = item.quantity + 1)
        }
    }

    fun decreaseQuantity(item: CartItem) {
        val index = _cartItems.indexOfFirst { it.id == item.id }
        if (index != -1 && item.quantity > 1) {
            _cartItems[index] = item.copy(quantity = item.quantity - 1)
        }
    }

    fun clearCart() {
        _cartItems.clear()
    }

    val totalPrice: Double
        get() = _cartItems.sumOf { it.price * it.quantity }
}