package com.example.splashscreenbaskit.Carts


data class CartItem(
    val id: String,
    val name: String,
    val weight: String,
    val quantity: Int,
    val price: Double,
    val imageResId: String
)

data class Product(
    val name: String,
    val imageRes: Int,
    val category: String,
    val price: Double,
)
