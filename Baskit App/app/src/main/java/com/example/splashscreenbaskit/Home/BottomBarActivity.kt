package com.example.splashscreenbaskit.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarActivity(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home : BottomBarActivity(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Cart : BottomBarActivity(
        route = "cart",
        title = "Cart",
        icon = Icons.Default.ShoppingCart
    )
    object Account : BottomBarActivity(
        route = "account",
        title = "Account",
        icon = Icons.Default.AccountCircle
    )
}