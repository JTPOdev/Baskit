package com.example.splashscreenbaskit.Tagabili

import Order
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.delay

@Composable
fun TB_HomeContent(navController: NavController) {
    val scrollState = rememberScrollState()
    var selectedLocation by remember { mutableStateOf("Select location --") }
    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var refreshing by remember { mutableStateOf(false) }

    var totalDagupanOrders by remember { mutableStateOf(0) }
    var totalCalasiaoOrders by remember { mutableStateOf(0) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = refreshing)

    val fetchOrders = { location: String ->
        loading = true
        refreshing = true
        OrderController(lifecycleOwner, context).fetchOrders(
            onResult = { success, message, fetchedOrders ->
                loading = false
                refreshing = false
                if (success && fetchedOrders != null) {
                    orders = fetchedOrders
                    errorMessage = null
                } else {
                    orders = emptyList()
                    errorMessage = message
                }
            },
            location = location
        )

        OrderController(lifecycleOwner, context).fetchTotalOrdersByLocation { success, message, totals ->
            if (success && totals != null) {
                totalDagupanOrders = totals.total_dagupan_orders
                totalCalasiaoOrders = totals.total_calasiao_orders
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchOrders(selectedLocation)
    }

    LaunchedEffect(selectedLocation) {
        if (selectedLocation != "Select location --") {
            fetchOrders(selectedLocation)
        }
    }

    // Wrap content in SwipeRefresh
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            refreshing = true
            fetchOrders(selectedLocation)
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                        .background(Color(0xFF1d7151))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(45.dp))

                        Image(
                            painter = painterResource(id = R.drawable.baskitlogo_white),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(70.dp)
                                .padding(bottom = 16.dp)
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
                                    horizontalAlignment = Alignment.CenterHorizontally
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
                                    horizontalAlignment = Alignment.CenterHorizontally
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

                Spacer(modifier = Modifier.height(100.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "ORDERS",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    // Location Selector
                    LocationButton(selectedLocation, { location ->
                        selectedLocation = location
                    })

                    Spacer(modifier = Modifier.height(20.dp))

                    if (loading) {
                        Text("Loading orders...", color = Color.Black)
                    } else if (errorMessage != null) {
                        Text("Error: $errorMessage", color = Color.Red)
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
    val locations = listOf("Select location --", "Dagupan", "Calasiao")
    var expanded by remember { mutableStateOf(false) }

    Button(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = selectedLocation, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
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
        title = { Text("Select Location") },
        text = {
            Column {
                locations.forEach { location ->
                    TextButton(onClick = {
                        onLocationChange(location)
                        onDismiss()
                    }) {
                        Text(location, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
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
                else -> {}
            }
        }
    }
}

@Composable
fun DagupanOrders(order: Order, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.baskit_green),
                contentDescription = "baskit green",
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
            ) {
                Text(
                    text = "${order.firstname} ${order.lastname}",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${order.total_orders} items",
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF737272)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                TextButton(onClick = { navController.navigate("tb_orders/${order.user_id}")}) {
                    Text("View Order", fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
                }
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "View Order",
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun CalasiaoOrders(order: Order, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.baskit_green),
                contentDescription = "baskit green",
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
            ) {
                Text(
                    text = "${order.firstname} ${order.lastname}",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${order.total_orders} items",
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF737272)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                TextButton(onClick = { navController.navigate("tb_orders/${order.user_id}")}) {
                    Text("View Order", fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
                }
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "View Order",
                    modifier = Modifier.size(15.dp)
                )
            }
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
