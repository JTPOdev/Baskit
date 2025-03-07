package com.example.splashscreenbaskit.Carts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import com.example.splashscreenbaskit.viewmodel.CartViewModel
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview(){
    val cartViewModel = CartViewModel()
    val navController = rememberNavController()
    CheckoutScreen(cartViewModel, navController)
}

@Composable
fun CheckoutScreen(cartViewModel: CartViewModel, navController: NavController) {
    val cartItems by remember { mutableStateOf(cartViewModel.cartItems) }
    var showCodeDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize() .verticalScroll(scrollState)
    ) {
        // Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp, start = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
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

        val totalPrice = cartItems.sumOf { it.price * it.quantity }


            Divider(
                thickness = 0.5.dp,
                color = Color.Black
            )
            Text(
                text = "Waiting for Tagabili...",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(top = 25.dp, bottom = 8.dp)
            )
//            Text(
//                text = "Tagabili: Juan Sebastian",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.SemiBold,
//                fontFamily = poppinsFontFamily,
//                modifier = Modifier.padding(top = 25.dp, bottom = 8.dp)
//            )
//            Text(
//                text = "0900-000-0000",
//                fontSize = 16.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Black
//            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .width(263.dp)
                    .height(60.dp)
                    .background(Color(0xFFEEEDED), shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "GCash and Cash are accepted",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5F5D5D)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row (
                modifier = Modifier.fillMaxWidth() .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Subtotal:",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Text(
                    text = "₱${"%.2f".format(totalPrice)}",
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
                    text = "Tagabili Fee:",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Text(
                    text = "₱0.00",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            HorizontalDivider(
                modifier = Modifier.padding(top = 15.dp, bottom = 8.dp),
                thickness = 0.5.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(7.dp))

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
                    text = "₱${"%.2f".format(totalPrice)}",
                    fontSize = 24.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF83BD70)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {showCodeDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF83BD70)),
                modifier = Modifier.width(205.dp) .height(58.dp)
            ) {
                Text(text = "PAY IN STORE", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)

            }
        }
    }

    if (showCodeDialog) {
        YourCodeDialog(
            onDismiss = { showCodeDialog = false },
            onSaveImage = {  }
        )
    }
}

@Composable
fun YourCodeDialog(onDismiss: () -> Unit, onSaveImage: () -> Unit) {
    val randomCode by remember { mutableStateOf(generateRandomCode()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
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
                // Back Button
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
                    color = Color.Black
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

                Box(
                    modifier = Modifier
                        .background(color = Color(0xFFB7DDCF), shape = RoundedCornerShape(50.dp))
                        .wrapContentWidth()
                        .padding(vertical = 10.dp, horizontal = 30.dp)
                ) {
                    Text(
                        text = randomCode,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Order processing...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = Color(0xFF8C8C8C)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {  },
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
                        Text(text = "Save as Image", fontSize = 14.sp, color = Color.Black, fontFamily = poppinsFontFamily,)
                    }
                }
            }
        }
    }
}

fun generateRandomCode(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..8).map { chars.random() }.joinToString("")
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
            Image(
                painter = painterResource(id = item.imageResId ?: R.drawable.noorders_img),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily)
                Text(text = "${item.quantity}pcs",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily)
            }
            Text(
                text = "₱${"%.2f".format(item.price * item.quantity)}",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                color = Color(0xFF83BD70),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
