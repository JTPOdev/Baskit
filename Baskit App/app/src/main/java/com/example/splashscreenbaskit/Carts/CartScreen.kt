package com.example.splashscreenbaskit.Carts

import CartItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.controller.CartController

@Preview (showBackground = true)
@Composable
fun CartPreview(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val apiService = RetrofitInstance.create(ApiService::class.java)

    val cartController = remember { CartController(lifecycleOwner, context, apiService) }

    CartScreen(cartController = cartController, navController = navController)
}
@Composable
fun CartScreen(cartController: CartController, navController: NavController) {
    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    //var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        cartController.fetchCartItems { success, message, items ->
            //isLoading = false
            if (success) {
                cartItems = items ?: emptyList()
            } else {
                errorMessage = message
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "My Basket",
            fontSize = 24.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Loading Indicator
//        if (isLoading) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//            return
//        }

        // Display Error Message (if any)
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(20.dp)
            )
        }

        // Product List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
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
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        CartItemView(
                            item = item,
                            onRemoveItem = {
                                cartController.removeFromCart(
                                    item.product_id,
                                    item.product_portion
                                ) { success, message ->
                                    if (success) {
                                        cartItems =
                                            cartItems.filter { it.product_id != item.product_id || it.product_portion != item.product_portion }
                                    } else {
                                        errorMessage = message
                                    }
                                }
                            },
                            onIncreaseQuantity = {
                                cartController.updateCart(
                                    item.product_id,
                                    item.product_quantity + 1,
                                    item.product_portion
                                ) { success, message ->
                                    println("Updating cart: product_id=${item.product_id}, product_quantity=${item.product_quantity}, product_portion=${item.product_portion}")
                                    if (success) {
                                        cartItems = cartItems.map {
                                            if (it.product_id == item.product_id && it.product_portion == item.product_portion)
                                                it.copy(product_quantity = it.product_quantity + 1)
                                            else it
                                        }.toList()
                                    } else {
                                        errorMessage = message
                                    }
                                }
                            },
                            onDecreaseQuantity = {
                                if (item.product_quantity > 1) {
                                    cartController.updateCart(
                                        item.product_id,
                                        item.product_quantity - 1,
                                        item.product_portion
                                    ) { success, message ->
                                        println("Updating cart: product_id=${item.product_id}, product_quantity=${item.product_quantity - 1}, product_portion=${item.product_portion}")
                                        if (success) {
                                            cartItems = cartItems.map {
                                                if (it.product_id == item.product_id && it.product_portion == item.product_portion)
                                                    it.copy(product_quantity = it.product_quantity - 1)
                                                else it
                                            }
                                        } else {
                                            errorMessage = message
                                        }
                                    }
                                } else {
                                    println("Quantity is already at minimum (1), cannot decrease further.")
                                }
                            },
                        )
                    }
                }
            }
        }

        // Checkout Section
        if (cartItems.isNotEmpty()) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(242.dp)
                .background(color = Color.White, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            ){

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val totalPrice = cartItems.sumOf { it.product_price * it.product_quantity }
                    val totalItems = cartItems.sumOf { it.product_quantity }

                    //HorizontalDivider(thickness = 0.5.dp, color = Color.Black)
                    Row (
                        modifier = Modifier.fillMaxWidth() .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "Items:",
                            fontSize = 16.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                        Text(
                            text = "$totalItems",
                            fontSize = 16.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth() .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "Total:",
                            fontSize = 24.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF83BD70)
                        )
                        Text(
                            text = "₱${String.format("%.2f", totalPrice)}",
                            fontSize = 24.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF83BD70)
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))


                    Button(
                        onClick = { navController.navigate("CheckoutScreen") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF83BD70)),
                        modifier = Modifier.width(205.dp) .height(58.dp)
                    ) {
                        Text(text = "Checkout", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)
                    }
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
            .padding(5.dp),
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
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = rememberImagePainter(item.product_image ?: "default_image_url"),
                        contentDescription = "Product Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = item.product_name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                    Text(
                        text = item.product_portion + " | "  + item.store_name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        color = Color(0xFF4D4D4D)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "₱${"%.2f".format(item.product_price)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily,
                        color = Color(0xFF4D4D4D)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = onRemoveItem
                    ) {
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
                        Text(
                            text = item.product_quantity.toString(),
                            fontSize = 15.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
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