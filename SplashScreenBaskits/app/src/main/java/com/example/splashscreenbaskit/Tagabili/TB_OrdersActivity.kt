package com.example.splashscreenbaskit.Tagabili

import Order
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controllers.OrderController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun TB_OrdersContent(navController: NavController, userId: Int) {
    val scrollState = rememberScrollState()
    val orders = remember { mutableStateOf<List<Order>?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Fetch user orders in LaunchedEffect
    LaunchedEffect(userId) {
        isLoading.value = true
        val fetchedOrders = withContext(Dispatchers.IO) {
            OrderController(lifecycleOwner, context).fetchUserOrders(userId)
        }
        orders.value = fetchedOrders
        isLoading.value = false
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
            if (isLoading.value) {
                Text("Loading...", modifier = Modifier.padding(16.dp))
            } else if (orders.value.isNullOrEmpty()) {
                Text("No orders found", modifier = Modifier.padding(16.dp), color = Color.Gray)
            } else {
                orders.value?.let { orderList ->
                    // Extract customer information from the first order
                    val customer = orderList.firstOrNull()
                    if (customer != null) {
                        TB_CustomerInfo(
                            name = customer.firstname,
                            branch = customer.product_origin.lowercase().replaceFirstChar { it.uppercase() },
                            contactNumber = customer.mobile_number
                        )
                    }
                    TB_OrderItems(orderList)
                }
            }
        }
    }
}


@Composable
fun TB_CustomerInfo(name: String, branch: String, contactNumber: String) {
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
                    text = name,
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

@Composable
fun TB_OrderItems(orders: List<Order>) {
    val groupedOrders = orders.groupBy { it.store_name }

    // Compute total price
    val totalPrice = orders.sumOf { it.product_price.toDouble() * it.product_quantity }

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
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(30.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                groupedOrders.forEach { (storeName, storeOrders) ->
                    // Display Store Name
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(250.dp)
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

                    // Display Products under this Store
                    storeOrders.forEach { order ->
                        ProductDetails(order)
                    }

                    // Store Separator
                    Divider(
                        color = Color.Black,
                        thickness = 0.8.dp,
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                    )
                }
            }
        }

        // Display Total Price
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
                text = "₱ ${"%,.2f".format(totalPrice)}", // Format to 2 decimal places
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = poppinsFontFamily
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .height(50.dp)
                    .width(147.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE22727)),
                enabled = true
            ) {
                Row {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Decline",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Decline",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.width(25.dp))

            Button(
                modifier = Modifier
                    .height(50.dp)
                    .width(147.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d7151)),
                enabled = true
            ) {
                Row {
                    Icon(
                        Icons.Default.Done,
                        contentDescription = "Accept",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Accept",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily
                    )
                }
            }
        }
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


