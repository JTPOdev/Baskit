package com.example.splashscreenbaskit.Tagabili

import AcceptOrderRequest
import Order
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controllers.OrderController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview(showBackground = true)
@Composable
fun TB_OrdersActivityPreview() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntry
    val userId = backStackEntry?.arguments?.getString("user_id")?.toIntOrNull()
        ?: 0

    TB_OrdersContent(navController, userId)
}
@Composable
fun TB_OrdersContent(navController: NavController, userId: Int) {
    val scrollState = rememberScrollState()
    val orders = remember { mutableStateOf<List<Order>?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val orderController = OrderController(lifecycleOwner, context)

    fun refreshOrders() {
        coroutineScope.launch {
            isLoading.value = true
            val fetchedOrders = withContext(Dispatchers.IO) {
                orderController.fetchUserOrders(userId)
            }
            orders.value = fetchedOrders
            isLoading.value = false
        }
    }

    LaunchedEffect(userId) {
        isLoading.value = true
        val fetchedOrders = withContext(Dispatchers.IO) {
            OrderController(lifecycleOwner, context).fetchUserOrders(userId)
        }
        orders.value = fetchedOrders
        isLoading.value = false
        refreshOrders()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background (Color.White)
            ){
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(top = 60.dp, start = 25.dp)
                        .size(35.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "ORDER\nSUMMARY",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(top = 140.dp, start = 40.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.orders_img),
                    contentDescription = "orders",
                    modifier = Modifier
                        .padding(start = 210.dp, top = 60.dp)
                        .height(157.dp)
                        .width(160.dp)
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 20.dp, bottomStart = 5.dp, bottomEnd = 20.dp))
                )

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background (Color(0xFFE0F4DE))
            ){
                Column (
                    modifier = Modifier.fillMaxSize() .padding(30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoading.value) {
                        Text(
                            "Loading...",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else if (orders.value.isNullOrEmpty()) {
                        Text(
                            "No orders found",
                            modifier = Modifier.padding(20.dp),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    } else {
                        orders.value?.let { orderList ->
                            val customer = orderList.firstOrNull()
                            if (customer != null) {
                                TB_CustomerInfo(
                                    name = customer.firstname,
                                    lastname = customer.lastname,
                                    branch = customer.product_origin.lowercase()
                                        .replaceFirstChar { it.uppercase() },
                                    contactNumber = customer.mobile_number
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            TB_OrderItems(orderList, orderController, navController, ::refreshOrders)
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun TB_CustomerInfo(name: String, lastname: String, branch: String, contactNumber: String) {
    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(30.dp))
            .height(152.dp)
            .width(339.dp)
            .padding(30.dp)
    ) {
        Column {
            Row {
                Text(
                    text = "Name:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )

                Text(
                    text = name + " " + lastname,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(
                    text = "Branch:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )

                Text(
                    text = branch,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(
                    text = "Contact No.:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily
                )

                Text(
                    text = contactNumber,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

fun generateRandomCode(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..5).map { chars.random() }.joinToString("")
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
        text = "Waiting for Pickup" + ".".repeat(dotCount),
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = poppinsFontFamily
    )
}
@Composable
fun TB_OrderItems(orders: List<Order>, orderController: OrderController, navController: NavController, refreshOrders: () -> Unit) {
    val groupedOrders = orders.groupBy { it.store_name }
    val context = LocalContext.current
    val totalPrice = orders.sumOf { it.product_price.toDouble() * it.product_quantity }

    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var originalOrderCode by rememberSaveable { mutableStateOf("") }
    var orderCode by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ITEMS",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(30.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                groupedOrders.forEach { (storeName, storeOrders) ->
                    val orderStatus = storeOrders.first().status

                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .width(195.dp)
                            .background(Color(0xFFD9D9D9), shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = storeName,
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily
                        )
                    }

                    storeOrders.forEach { order ->
                        ProductDetails(order)
                    }
                }
            }
        }

        Column(
            modifier = Modifier.width(320.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                color = Color.Black,
                thickness = 0.8.dp,
                modifier = Modifier.padding(top = 30.dp, bottom = 20.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = poppinsFontFamily
            )

            Spacer(modifier = Modifier.width(120.dp))

            Text(
                text = "₱ ${"%,.2f".format(totalPrice)}",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = poppinsFontFamily
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                groupedOrders.forEach { (_, storeOrders) ->
                    val orderStatus = storeOrders.first().status

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (orderStatus == "Pending") {
                            Button(
                                modifier = Modifier.height(50.dp).width(147.dp),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    navController.popBackStack()
                                    refreshOrders()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFE22727
                                    )
                                )
                            ) {
                                Row {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Decline",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "Decline",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(25.dp))

                            Button(
                                modifier = Modifier.height(50.dp).width(147.dp),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    isLoading = true
                                    orderController.acceptOrder(
                                        generateRandomCode(),
                                        storeOrders.first().user_id
                                    ) { success, message ->
                                        isLoading = false
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                        refreshOrders()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF1d7151
                                    )
                                )
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Row {
                                        Icon(
                                            Icons.Default.Done,
                                            contentDescription = "Accept",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Accept",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        } else if (orderStatus == "Accepted") {
                            val orderReadyStatus by rememberUpdatedState(storeOrders.first().is_ready)

                            if (orderReadyStatus == "Ready") {
                                AnimatedWaitingText()
                            } else {
                                Button(
                                    modifier = Modifier.height(50.dp).width(200.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        showDialog = true
                                        originalOrderCode = storeOrders.first().order_code
                                        orderCode = ""
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFFfbc02d
                                        )
                                    )
                                ) {
                                    Text(
                                        "Order Ready?",
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "Confirm Order Ready?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Make sure that the code is correct!", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Code: $originalOrderCode",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = orderCode,
                        onValueChange = {
                            if (it.length <= 5) orderCode =
                                it.uppercase()
                        },
                        label = { Text("Enter Code", fontSize = 16.sp) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Characters
                        ),
                        textStyle = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .width(180.dp)
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        isLoading = true
                        orderController.readyOrder(orderCode, 0) { success, message ->
                            isLoading = false
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            if (success) {
                                showDialog = false
                                refreshOrders()
                            }
                        }
                    },
                    enabled = orderCode == originalOrderCode,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d7151)),
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Ready", color = Color.White, fontSize = 16.sp)
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE22727)),
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                ) {
                    Text("Cancel", color = Color.White, fontSize = 16.sp)
                }
            }
        )
    }
}

@Composable
fun ProductDetails(order: Order) {
    Row(modifier = Modifier.padding(top = 20.dp)) {
        Column {
            Text(
                text = order.product_name,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily
            )

            Row {
                Text(
                    text = "${order.product_quantity} pc",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily
                )

                Text(
                    text = "Quantity:",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 15.dp)
                )

                Text(
                    text = order.product_quantity.toString(),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        Text(
            text = "₱ ${order.product_price}",
            color = Color.DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            modifier = Modifier.padding(start = 100.dp, top = 10.dp)
        )
    }
}