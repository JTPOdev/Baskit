package com.example.splashscreenbaskit.Carts

import CartItem
import Order
import OrderResponse
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.controller.CartController
import com.example.splashscreenbaskit.controllers.OrderController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
@Preview(showBackground = true)
@Composable
fun CheckoutPreview(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val apiService = RetrofitInstance.create(ApiService::class.java)

    val cartController = remember { CartController(lifecycleOwner, context, apiService) }
    CheckoutScreen(cartController = cartController, navController = navController)
}
@Composable
fun CheckoutScreen(cartController: CartController, navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var showCodeDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    var orderResponse by remember { mutableStateOf<String?>(null) }
    val orderCode = cartItems.firstOrNull()?.order_code ?: "N/A"
    val isReady = cartItems.firstOrNull()?.is_ready ?: "N/A"
    var currentStatus by remember { mutableStateOf<String?>(null) }

    var isPlacingOrder by remember { mutableStateOf(false) }
    var isWaitingForAcceptance by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val firstItem = cartItems.firstOrNull()
    val orderStatus = firstItem?.order_status
    val Status = firstItem?.status
    val tagabiliFirstname = firstItem?.tagabili_firstname + " " + firstItem?.tagabili_lastname ?: "Unknown"
    val tagabiliMobile = firstItem?.tagabili_mobile ?: "N/A"
    val tagabiliEmail = firstItem?.tagabili_email ?: "N/A"

    val tagabiliFee = cartItems.firstOrNull()?.tagabili_fee ?: 0.0
    val productFee = cartItems.sumOf { it.product_fee }
    val totalFee = tagabiliFee + productFee

    val orderController = remember { OrderController(lifecycleOwner, context) }

    // Fetch cart items when screen loads
    LaunchedEffect(cartItems) {
        cartController.fetchCartItems { success, message, items ->
            if (success && items != null) {
                cartItems = items
            } else {
                Log.e("CheckoutScreen", message ?: "Error fetching cart")
            }
        }

        while (cartItems.any { it.status != "Accepted" }) {
            delay(5000)

            cartController.fetchCartItems { success, message, items ->
                if (success && items != null) {
                    cartItems = items
                } else {
                    Log.e("CheckoutScreen", message ?: "Error fetching cart")
                }
            }
        }
    }

    LaunchedEffect(Status) {
        if (Status == "Accepted") {
            isWaitingForAcceptance = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        // Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (cartItems.any { it.status == "Accepted" }) {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    } else {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.size(35.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(80.dp))
            Text(
                text = "Checkout",
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .weight(1f)
        ) {
            LazyColumn {
                items(cartItems) { item ->
                    CheckoutItemView(item = item)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val totalPrice = cartItems.sumOf { it.product_price * it.product_quantity }

            Divider(thickness = 0.5.dp, color = Color.Black)

            // Get order status and Tagabili details
            if (Status == "Accepted") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text(
                        text = "Your Tagabili:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily,
                        color = Color(0xFF83BD70)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "$tagabiliFirstname",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black
                    )
                    Text(
                        text = "$tagabiliMobile",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black
                    )
                    Text(
                        text = "$tagabiliEmail",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black
                    )
                }
            } else {
                AnimatedWaitingText()
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .width(263.dp)
                    .height(60.dp)
                    .background(Color(0xFFEEEDED), shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "GCash and Cash are accepted",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5F5D5D)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal:",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Text(
                    text = "₱${String.format("%.2f", totalPrice)}",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tagabili Fee:",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Text(
                    text = "₱${String.format("%.2f", totalFee)}",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Divider(thickness = 0.5.dp, color = Color.Black)

            Spacer(modifier = Modifier.height(9.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total:",
                    fontSize = 24.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF83BD70)
                )
                Text(
                    text = "₱${String.format("%.2f", totalPrice + totalFee)}",
                    fontSize = 24.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF83BD70)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    if (Status == "Accepted") {
                        showCodeDialog = true
                    } else if (orderStatus == "Pending") {
                        showConfirmationDialog = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF83BD70)),
                modifier = Modifier.wrapContentWidth().height(58.dp),
                enabled = !(isPlacingOrder || isWaitingForAcceptance || (orderStatus == "Order Placed" && Status !in listOf("Accepted", "Completed", "Done")))
            ) {
                if (isPlacingOrder || isWaitingForAcceptance || (orderStatus == "Order Placed" && Status !in listOf("Accepted", "Completed", "Done"))) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(
                    text = when {
                        currentStatus == "Ready" -> "Order Received?"
                        Status == "Accepted" -> "YOUR CODE"
                        isPlacingOrder || isWaitingForAcceptance || orderStatus == "Order Placed" -> "Order Pending"
                        else -> "PAY IN STORE"
                    },
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )
            }
        }

        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Confirm Checkout",
                            fontSize = 18.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                text = {
                    Text(
                        "Are you sure you want to\nproceed and pay in store?",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB00020), // Red color
                                contentColor = Color.White
                            ),
                            onClick = { showConfirmationDialog = false }
                        ) {
                            Text(
                                "Cancel",
                                fontSize = 15.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1d7151), // Green color
                                contentColor = Color.White
                            ),
                            onClick = {
                                showConfirmationDialog = false
                                isPlacingOrder = true
                                cartController.placeOrder { success, message ->
                                    isPlacingOrder = false
                                    orderResponse = message
                                    if (success) {
                                        isWaitingForAcceptance = true
                                    }
                                }
                            }
                        ) {
                            Text(
                                "Confirm",
                                fontSize = 15.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            )
        }

        if (showCodeDialog) {
            YourCodeDialog(
                orderCode = orderCode,
                cartController = cartController,
                orderController = orderController,
                Status = Status,
                onDismiss = { showCodeDialog = false },
                navController = navController
            )
        }

        orderResponse?.let { response ->
            LaunchedEffect(response) {
                if (!response.contains("success", true)) {
                    Log.e("CheckoutScreen", response)
                }
            }
        }
    }
}

@Composable
fun AnimatedWaitingText() {
    var dotCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            dotCount = (dotCount + 1) % 4
        }
    }

    Text(
        text = "Waiting for Tagabili" + ".".repeat(dotCount),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily,
        modifier = Modifier.padding(top = 25.dp, bottom = 8.dp),
        color = Color.Black
    )
}


@Composable
fun AnimatedProcessingText() {
    var dotCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            dotCount = (dotCount + 1) % 4
        }
    }

    Text(
        text = "Order processing" + ".".repeat(dotCount),
        color = Color.Gray,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily
    )
}

@Composable
fun YourCodeDialog(
    orderCode: String,
    cartController: CartController,
    orderController: OrderController,
    Status: String?,
    onDismiss: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewRef = remember { mutableStateOf<View?>(null) }
    var currentStatus by remember { mutableStateOf<String?>(null) }

    // Fetch cart items to get the current order status
    LaunchedEffect(Unit) {
        cartController.fetchCartItems { success, _, items ->
            if (success && items != null) {
                currentStatus = items.firstOrNull()?.is_ready ?: "Pending"
            }
        }
    }

    // Periodically check order status
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            cartController.fetchCartItems { success, _, items ->
                if (success && items != null) {
                    currentStatus = items.firstOrNull()?.is_ready ?: "Pending"
                }
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                Text(
                    text = "Present this code to\nour staff upon pickup.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "YOUR CODE",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                AndroidView(
                    factory = { ctx ->
                        val textView = TextView(ctx).apply {
                            text = orderCode
                            textSize = 32f
                            setTypeface(typeface, Typeface.BOLD)
                            setBackgroundColor(android.graphics.Color.parseColor("#B7DDCF"))
                            setPadding(30, 10, 30, 10)
                            setTextColor(android.graphics.Color.BLACK)
                            gravity = Gravity.CENTER
                        }
                        viewRef.value = textView
                        textView
                    },
                    modifier = Modifier
                        .background(color = Color(0xFFB7DDCF), shape = RoundedCornerShape(50.dp))
                        .wrapContentWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                when (currentStatus) {
                    "Pending" -> AnimatedProcessingText()
                    "Ready" -> Text(
                        text = "Order is now ready\nfor pickup!",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center
                    )
                    "Completed" -> Text(
                        text = "Order is now complete",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // "Mark as Done" button
                if (currentStatus == "Completed") {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                orderController.doneOrder(orderCode) { success, message ->
                                    if (success) {
                                        currentStatus = "Done"
                                        navController.navigate("home")
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d7151)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Received?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = poppinsFontFamily
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        viewRef.value?.let { view ->
                            saveViewAsImage(context, view)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.save),
                            contentDescription = "Save as Image",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Save as Image",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = poppinsFontFamily
                        )
                    }
                }
            }
        }
    }
}

fun saveViewAsImage(context: Context, view: View) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)

    val filename = "order_code_${System.currentTimeMillis()}.png"
    val fos: OutputStream?

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/OrderCodes")
        }

        val imageUri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        fos = imageUri?.let { context.contentResolver.openOutputStream(it) }
    } else {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "OrderCodes"
        )
        if (!directory.exists()) directory.mkdirs()
        val file = File(directory, filename)
        fos = FileOutputStream(file)
    }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        Toast.makeText(context, "Saved to gallery", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CheckoutItemView(item: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )
                Text(
                    text = "${item.product_quantity} of ${item.product_portion ?: "N/A"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily
                )
            }
            Text(
                text = "₱${"%.2f".format(item.product_price * item.product_quantity)}",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                color = Color(0xFF83BD70),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
