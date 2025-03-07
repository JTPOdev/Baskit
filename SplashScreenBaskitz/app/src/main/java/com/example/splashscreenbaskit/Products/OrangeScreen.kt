//package com.example.splashscreenbaskit.Products
//
//import android.widget.Toast
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.splashscreenbaskit.AccountDetails.NotificationsActivity
//import com.example.splashscreenbaskit.R
//import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
//import com.example.splashscreenbaskit.Carts.CartItem
//import com.example.splashscreenbaskit.Tagabili.TB_OrdersContent
//import com.example.splashscreenbaskit.viewmodel.CartViewModel
//import androidx.compose.ui.platform.LocalContext
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun OrangeScreenPreview() {
//    val navController = rememberNavController()
//    val cartViewModel = CartViewModel() // Create a mock ViewModel instance
//    OrangeScreen(navController = navController, cartViewModel = cartViewModel)
//}
//
//@Composable
//fun OrangeScreen(navController: NavController, cartViewModel: CartViewModel) {
//    val context = LocalContext.current
//    var quantity by remember { mutableStateOf(1) }
//    var selectedWeight by remember { mutableStateOf("1 pc") }
//    val basePrice = 32.25
//    val priceIncrease = 30.0
//
//    val priceForWeight = when (selectedWeight) {
//        "1 pc" -> basePrice
//        "1/4 kg" -> basePrice + priceIncrease
//        "1/2 kg" -> basePrice + priceIncrease
//        "1 kg" -> basePrice + priceIncrease * 2
//        else -> basePrice
//    }
//    val totalPrice = priceForWeight * quantity
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // Image Section
//        // Image Section (Rectangle)
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(250.dp)
//                .background(Color.White),
//            contentAlignment = Alignment.TopCenter
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.orange),
//                contentDescription = "Orange",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp)
//                    .clip(RoundedCornerShape(10.dp)),
//                contentScale = ContentScale.Crop
//            )
//
//            IconButton(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .align(Alignment.TopStart)
//                    .size(40.dp)
//                    .background(Color.White, shape = CircleShape)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.back),
//                    contentDescription = "Back",
//                    tint = Color.Black,
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        // Product Details
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(0.35f)
//                .padding(horizontal = 16.dp)
//        ) {
//            Text(
//                text = "Orange",
//                fontSize = 36.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "₱ ${"%.2f".format(priceForWeight)}",
//                    fontSize = 28.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Gray
//                )
//
//                // Quantity Buttons
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    IconButton(
//                        onClick = { if (quantity > 1) quantity-- },
//                        modifier = Modifier
//                            .background(color = Color(0xFFD9D9D9), shape = CircleShape)
//                            .size(35.dp)
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.minus),
//                            contentDescription = "Minus",
//                            tint = Color.Black,
//                            modifier = Modifier.size(15.dp)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    Text(
//                        text = "$quantity",
//                        fontSize = 20.sp,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.width(12.dp))
//
//                    IconButton(
//                        onClick = { quantity++ },
//                        modifier = Modifier
//                            .background(color = Color(0xFFD9D9D9), shape = CircleShape)
//                            .size(35.dp)
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.add),
//                            contentDescription = "Add",
//                            tint = Color.Black,
//                            modifier = Modifier.size(15.dp)
//                        )
//                    }
//                }
//
//            }
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Text(
//                text = "Seller Description",
//                fontSize = 20.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(vertical = 8.dp),
//                color = Color.Gray
//            )
//            Text(
//                text = "Martha Rosario (Aling Martha’s)",
//                fontSize = 20.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Black
//            )
//
//            Text(
//                text = "0900-000-0000",
//                fontSize = 18.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.Light,
//                color = Color.Black
//            )
//
//            Text(
//                text = "123 Street, Dagupan City",
//                fontSize = 18.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.Light,
//                color = Color.Black
//            )
//
//            Spacer(modifier = Modifier.height(143.dp))
//
//            // Weight Selection
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                listOf("1 pc", "1/4 kg", "1/2 kg", "1 kg").forEach { option ->
//                    Button(
//                        modifier = Modifier
//                            .height(55.dp)
//                            .width(85.dp)
//                            .shadow(8.dp, shape = RoundedCornerShape(15.dp)) // Add shadow effect
//                            .clip(RoundedCornerShape(20.dp)),
//                        onClick = { selectedWeight = option },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = if (selectedWeight == option) Color(0xFFD9D9D9) else Color.White,
//                            contentColor = Color.Black
//                        ),
//                    ) {
//                        Text(
//                            text = option,
//                            fontSize = 12.sp,
//                            fontFamily = poppinsFontFamily,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }
//                }
//            }
//        }
//
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(0.1f)
//                .background(Color(0xFF1D7151))
//                .padding(20.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                Text(
//                    text = "Total Price",
//                    fontSize = 16.sp,
//                    fontFamily = poppinsFontFamily,
//                    color = Color.White,
//                    fontWeight = FontWeight.SemiBold
//                )
//
//                Text(
//                    text = "₱ ${"%.2f".format(totalPrice)}",
//                    fontSize = 24.sp,
//                    fontFamily = poppinsFontFamily,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            Button(
//                onClick = {
//                    val itemId = System.currentTimeMillis().toInt()
//                    cartViewModel.addToCart(
//                        CartItem(
//                            id = itemId,
//                            name = "Orange",
//                            weight = selectedWeight,
//                            quantity = quantity,
//                            price = totalPrice,
//                            imageResId = R.drawable.orange
//                        )
//                    )
//                    Toast.makeText(context, "Added to Basket!", Toast.LENGTH_SHORT).show()
//                },
//                shape = RoundedCornerShape(30.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//                modifier = Modifier
//                    .height(60.dp)
//                    .width(180.dp)
//            ) {
//                Text(
//                    text = "Add to Basket",
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                )
//            }
//        }
//    }
//}
//
