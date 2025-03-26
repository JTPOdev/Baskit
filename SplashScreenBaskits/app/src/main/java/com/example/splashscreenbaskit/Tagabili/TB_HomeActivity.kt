package com.example.splashscreenbaskit.Tagabili

import Order
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controllers.OrderController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import com.google.accompanist.swiperefresh.*


@Composable
fun TB_HomeContent(navController: NavController) {
    val scrollState = rememberSaveable { mutableStateOf(0) }
    var selectedLocation by rememberSaveable { mutableStateOf("Select location --") }
    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }
    var loading by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }
    var refreshing by rememberSaveable { mutableStateOf(false) }

    var totalDagupanOrders by rememberSaveable { mutableStateOf(0) }
    var totalCalasiaoOrders by rememberSaveable { mutableStateOf(0) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = refreshing)

    val fetchOrders = { location: String ->
        loading = true
        refreshing = true

        val orderController = OrderController(lifecycleOwner, context)

        when (location) {
            "Your Orders" -> {
                orderController.fetchAcceptedOrders { success, message, fetchedOrders ->
                    loading = false
                    refreshing = false
                    orders = if (success) fetchedOrders ?: emptyList() else emptyList()
                    errorMessage = if (!success) message else null
                }
            }
            else -> {
                orderController.fetchOrders(
                    location = location,
                    onResult = { success, message, fetchedOrders ->
                        loading = false
                        refreshing = false
                        orders = if (success) fetchedOrders?.filter { it.status == "Pending" } ?: emptyList() else emptyList()
                        errorMessage = if (!success) message else null
                    }
                )
                orderController.fetchTotalOrdersByLocation { success, message, totals ->
                    if (success && totals != null) {
                        totalDagupanOrders = totals.total_dagupan_orders
                        totalCalasiaoOrders = totals.total_calasiao_orders
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchOrders(selectedLocation)
    }

    LaunchedEffect(Unit) {
        if (selectedLocation != "Select location --") {
            fetchOrders(selectedLocation)
        }
    }

    LaunchedEffect(selectedLocation) {
        if (selectedLocation != "Select location --") {
            fetchOrders(selectedLocation)
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            refreshing = true
            fetchOrders(selectedLocation)
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState(scrollState.value)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(363.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                        .background(Color(0xFF1d7151))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(45.dp))

                        Image(
                            painter = painterResource(id = R.drawable.baskitlogo_white),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(70.dp)
                                .padding(bottom = 15.dp)
                        )

                        Text(
                            text = "Shop Smarter, Not Harder",
                            fontFamily = poppinsFontFamily,
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.offset(y = (-20).dp)
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        // Pending Orders Section
                        Box(
                            modifier = Modifier
                                .background(Color.White, shape = RoundedCornerShape(20.dp))
                                .height(160.dp)
                                .width(330.dp)
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(
                                text = "PENDING ORDERS",
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 15.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier.padding(top = 65.dp, end = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Dagupan",
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 15.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = totalDagupanOrders.toString(),
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 36.sp,
                                        color = Color.Black
                                    )
                                }

                                Spacer(modifier = Modifier.width(50.dp))

                                Column(
                                    modifier = Modifier.padding(top = 65.dp, end = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Calasiao",
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 15.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = totalCalasiaoOrders.toString(),
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 36.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ORDERS",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(200.dp))
                        IconButton(
                            onClick = { navController.navigate("TB_AccountDetails") },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(Color.Black, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Account",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    LocationButton(selectedLocation, { location ->
                        selectedLocation = location
                    })

                    Spacer(modifier = Modifier.height(50.dp))

                    if (loading) {
                        Text("Loading orders...",
                            color = Color.Black,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    } else if (errorMessage != null) {
                        Text("Error: $errorMessage",
                            color = Color.Red,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    } else if (orders.isEmpty()) {
                        DefaultScreen()
                    } else {
                        OrdersList(orders, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LocationButton(selectedLocation: String, onLocationChange: (String) -> Unit) {
    val locations = listOf("Dagupan", "Calasiao", "Your Orders")
    var expanded by remember { mutableStateOf(false) }


    Button(
        onClick = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = Color.Black,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Text(
            text = selectedLocation,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }

    if (expanded) {
        LocationDialog(locations, onLocationChange) {
            expanded = false
        }
    }
}

@Composable
fun LocationDialog(locations: List<String>, onLocationChange: (String) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(
            "Select Location",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color.Black
        ) },
        text = {
            Column {
                locations.forEach { location ->
                    TextButton(
                        onClick = {
                            onLocationChange(location)
                            onDismiss()
                        }) {
                        Text(location,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE22727)),
                onClick = onDismiss
            ) {
                Text(
                    "Close",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun OrdersList(orders: List<Order>, navController: NavController) {
    Log.d("OrdersList", "Orders count: ${orders.size}")
    Column {
        orders.forEach { order ->
            when (order.product_origin.uppercase()) {
                "DAGUPAN" -> DagupanOrders(order, navController)
                "CALASIAO" -> CalasiaoOrders(order, navController)
                else -> YourOrders(order, navController)
            }
        }
    }
}

@Composable
fun DagupanOrders(order: Order, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(60.dp)
            .clickable { navController.navigate("tb_orders/${order.user_id}") },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baskit_green),
                contentDescription = "baskit green",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 20.dp, bottomStart = 10.dp, bottomEnd = 20.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = "${order.firstname} ${order.lastname}",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${order.total_orders} items",
                    fontSize = 10.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF737272)
                )
            }

            val statusColor = when {
                order.status == "Pending" -> Color(0xFFE22727) // Red
                order.status == "Accepted" && order.is_ready == "Pending" -> Color(0xFFFBC02D) // Yellow
                order.is_ready == "Ready" -> Color(0xFF1d7151) // Green
                else -> Color.Gray
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(statusColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun CalasiaoOrders(order: Order, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(60.dp)
            .clickable { navController.navigate("tb_orders/${order.user_id}") },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baskit_green),
                contentDescription = "baskit green",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 20.dp, bottomStart = 10.dp, bottomEnd = 20.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = "${order.firstname} ${order.lastname}",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${order.total_orders} items",
                    fontSize = 10.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF737272)
                )
            }

            val statusColor = when {
                order.status == "Pending" -> Color(0xFFE22727) // Red
                order.status == "Accepted" && order.is_ready == "Pending" -> Color(0xFFFBC02D) // Yellow
                order.is_ready == "Ready" -> Color(0xFF1d7151) // Green
                else -> Color.Gray
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(statusColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun YourOrders(order: Order, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(60.dp)
            .clickable { navController.navigate("tb_orders/${order.user_id}") },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baskit_green),
                contentDescription = "baskit green",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 20.dp, bottomStart = 10.dp, bottomEnd = 20.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = "${order.firstname} ${order.lastname}",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${order.total_orders} items",
                    fontSize = 10.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF737272)
                )
            }

            val statusColor = when {
                order.status == "Pending" -> Color(0xFFE22727) // Red
                order.status == "Accepted" && order.is_ready == "Pending" -> Color(0xFFFBC02D) // Yellow
                order.is_ready == "Ready" -> Color(0xFF1d7151) // Green
                else -> Color.Gray
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(statusColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun DefaultScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.noorders_img),
            contentDescription = "No Orders",
            modifier = Modifier.size(205.dp)
        )
        Text(
            text = "No location selected yet.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            color = Color(0xFF656565),
            modifier = Modifier.padding(10.dp)
        )
    }
}