package com.example.splashscreenbaskit.Carts

import CartItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.splashscreenbaskit.controller.CartController


@Composable
fun CartScreen(cartController: CartController, navController: NavController) {
    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        cartController.fetchCartItems { success, message, items ->
            isLoading = false
            if (success) {
                cartItems = items ?: emptyList()
            } else {
                errorMessage = message
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Title Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(65.dp))
            Text(
                text = "My Basket",
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Loading Indicator
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return
        }

        // Display Error Message (if any)
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Product List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            if (cartItems.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.noorders_img),
                        contentDescription = "No Orders",
                        modifier = Modifier.size(220.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Your basket is empty!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontFamily = poppinsFontFamily
                    )
                }
            } else {
                LazyColumn {
                    items(cartItems) { item ->
                        CartItemView(
                            item = item,
                            onRemoveItem = { /* Call API to remove item */ },
                            onIncreaseQuantity = { /* Call API to increase quantity */ },
                            onDecreaseQuantity = { /* Call API to decrease quantity */ }
                        )
                    }
                }
            }
        }

        // Checkout Section
        if (cartItems.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Gray.copy(alpha = 0.5f), Color.Transparent)
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val totalPrice = cartItems.sumOf { it.product_price * it.product_quantity }
                val totalItems = cartItems.sumOf { it.product_quantity }

                Text(
                    text = "ITEMS: $totalItems",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Total: ₱${"%.2f".format(totalPrice)}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6CBF5F)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { navController.navigate("CheckoutScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d7151)),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(text = "Checkout", color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun CartItemView(
    item: CartItem,
    onRemoveItem: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Load image using Coil
                Image(
                    painter = rememberImagePainter(item.product_image ?: "default_image_url"),
                    contentDescription = "Product Image",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.product_name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = item.product_portion ?: "N/A",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Text(
                        text = "₱${"%.2f".format(item.product_price)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6CBF5F)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = onRemoveItem) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Item",
                            tint = Color.Red
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onDecreaseQuantity) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = "Decrease Quantity",
                                tint = Color.Black,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Text(text = item.product_quantity.toString(), fontSize = 18.sp)
                        IconButton(onClick = onIncreaseQuantity) {
                            Icon(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "Increase Quantity",
                                tint = Color.Black,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}