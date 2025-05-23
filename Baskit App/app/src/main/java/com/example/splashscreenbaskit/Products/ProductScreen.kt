package com.example.splashscreenbaskit.Products

import ProductsResponse
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controller.CartController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import kotlinx.coroutines.delay
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.splashscreenbaskit.api.ApiService
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun ProductScreen(
    navController: NavController = rememberNavController(),
    productName: String,
    productsResponse: ProductsResponse,
    cartController: CartController
) {
    val product = productsResponse

    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(showDialog.value) {
        if (showDialog.value) {
            delay(1000)
            showDialog.value = false
        }
    }

    val context = LocalContext.current
    var quantity by remember { mutableStateOf(1) }
    var selectedWeight by remember { mutableStateOf("") }
    val basePrice = product.product_price.toDouble()
    val priceIncrease = 30.0
    val commisionFee = 40.0

    val priceForWeight by remember(selectedWeight) {
        mutableStateOf(
            when (selectedWeight) {
                "1pc" -> basePrice
                "1/4kg" -> basePrice + (priceIncrease * 0.25)
                "1/2kg" -> basePrice + (priceIncrease * 0.5)
                "1kg" -> basePrice + (priceIncrease * 1.0)
                else -> basePrice
            }
        )
    }
    val totalPrice = priceForWeight * quantity

    val feePerPiece = when (selectedWeight) {
        "1pc" -> 2.0 * quantity  // ₱2 per piece
        "1/4kg" -> priceForWeight * 0.04 * quantity // 4% fee
        "1/2kg" -> priceForWeight * 0.05 * quantity // 3% fee
        "1kg" -> priceForWeight * 0.06 * quantity  // 6% fee
        else -> 0.0
    }

    val onBackPressed = {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = rememberImagePainter(product.product_image),
                contentDescription = product.product_name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 60.dp, start = 20.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = product.product_name.replace("+", " "),
                fontSize = 32.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₱ ${"%.2f".format(priceForWeight)}",
                    fontSize = 24.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier
                            .background(color = Color(0xFFD9D9D9), shape = CircleShape)
                            .size(35.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "Minus",
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(25.dp))

                    Text(
                        text = "$quantity",
                        fontSize = 20.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(25.dp))

                    IconButton(
                        onClick = { quantity++ },
                        modifier = Modifier
                            .background(color = Color(0xFFD9D9D9), shape = CircleShape)
                            .size(35.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add",
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEEDED), shape = RoundedCornerShape(10.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        text = "Seller Description",
                        fontSize = 20.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = product.store_name.replace("+", " "),
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = product.store_phone_number,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = product.store_address.replace("+", " "),
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Log.d("NavigationDebug", "Navigating with product: ${Gson().toJson(product)}")
                Log.d("ProductDebug", "is1Pc: ${product.is1Pc}, is1_4Kg: ${product.is1_4Kg}, is1_2Kg: ${product.is1_2Kg}, is1Kg: ${product.is1Kg}")

                val weightOptions = listOf(
                    "1pc" to product.is1Pc,
                    "1/4kg" to product.is1_4Kg,
                    "1/2kg" to product.is1_2Kg,
                    "1kg" to product.is1Kg
                )

                val availableWeights = weightOptions.filter { it.second == 1 }.map { it.first }

                availableWeights.forEachIndexed { index, option ->
                    Button(
                        modifier = Modifier
                            .height(55.dp)
                            .width(85.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(10.dp)),
                        onClick = {
                            selectedWeight = option
                            Log.d("WeightSelection", "User selected: $selectedWeight")
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedWeight == option) Color(0xFFD9D9D9) else Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = option,
                            fontSize = 12.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                    if (index < availableWeights.size - 1) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color(0xFF1D7151))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Total Price",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "₱ ${"%.2f".format(totalPrice)}",
                    fontSize = 22.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Button(
                    onClick = {
                        if (selectedWeight.isNotEmpty()) {
                            cartController.addToCart(
                                productId = product.id,
                                productName = product.product_name,
                                productPrice = product.product_price.toDouble(),
                                productFee = feePerPiece,
                                productQuantity = quantity,
                                productPortion = selectedWeight,
                                productOrigin = product.product_origin,
                                storeId = product.store_id,
                                storeName = product.store_name,
                                productImageUrl = product.product_image ?: "",
                                orderStatus = "Pending",
                                status = "Pending",
                                tagabiliFee = commisionFee,
                                tagabiliFirstname = "Pending",
                                tagabiliLastname = "Pending",
                                tagabiliMobile = "Pending",
                                tagabiliEmail = "Pending",
                                orderCode = "Pending",
                                isReady = "Pending",
                            ) { response ->
                                if (response.success) {
                                    Toast.makeText(context, "Failed to add to Basket!", Toast.LENGTH_SHORT).show()
                                } else {
                                    showDialog.value = true
                                }
                            }
                        }
                    },
                    enabled = selectedWeight.isNotEmpty(),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedWeight.isEmpty()) Color.Gray else Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .height(58.dp)
                        .width(205.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = "Add to Basket",
                        color = if (selectedWeight.isEmpty()) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = poppinsFontFamily
                    )
                }

                if (showDialog.value) {
                    Popup(
                        onDismissRequest = { showDialog.value = false } ,
                        alignment = Alignment.Center
                    ) {
                        val scale = remember { Animatable(0f) } // START HIDDEN
                        val alpha = remember { Animatable(1f) } // START VISIBLE

                        LaunchedEffect(Unit) {
                            // POP IN
                            scale.animateTo(
                                1.2f,
                                animationSpec = tween(150, easing = FastOutSlowInEasing)
                            ) // OVERSHOOT
                            scale.animateTo(
                                1f,
                                animationSpec = tween(100, easing = FastOutSlowInEasing)
                            )  // SETTLE

                            delay(1200) // VISIBLE FOR 1.2s

                            // FADE
                            alpha.animateTo(
                                0f,
                                animationSpec = tween(300, easing = FastOutSlowInEasing)
                            ) // FADE OUT

                            showDialog.value = false
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .graphicsLayer(
                                        scaleX = scale.value,
                                        scaleY = scale.value,
                                        alpha = alpha.value
                                    )
                                    .shadow(12.dp, shape = RoundedCornerShape(20.dp))
                                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                                    .padding(horizontal = 30.dp, vertical = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Added to Basket!",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    fontFamily = poppinsFontFamily,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.added),
                                    contentDescription = "Checkmark",
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}