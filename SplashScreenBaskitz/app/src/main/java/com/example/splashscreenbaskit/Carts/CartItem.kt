package com.example.splashscreenbaskit.Carts


data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val weight: String? = null,
    val imageResId: Int? = null
)

data class Product(
    val name: String,
    val imageRes: Int,
    val category: String,
    val price: Double,
)
