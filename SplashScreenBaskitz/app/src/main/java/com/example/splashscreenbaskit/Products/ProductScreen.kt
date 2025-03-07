package com.example.splashscreenbaskit.Products

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.Carts.CartItem
import com.example.splashscreenbaskit.Carts.Product
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import com.example.splashscreenbaskit.viewmodel.CartViewModel

// Product lists for each category
val vegetableList = listOf(
    Product("Carrot", R.drawable.carrot, "Vegetables", 1.50),
    Product("Broccoli", R.drawable.brocolli, "Vegetables", 2.00),
    Product("Spinach", R.drawable.spinach, "Vegetables", 1.20),
    Product("Potato", R.drawable.potato, "Vegetables", 0.80)
)

val fruitList = listOf(
    Product("Apple", R.drawable.apple, "Fruits", 0.50),
    Product("Orange", R.drawable.orange, "Fruits", 0.60),
    Product("Banana", R.drawable.banana, "Fruits", 0.30),
    Product("Mango", R.drawable.mango, "Fruits", 1.00),
    Product("Grapes", R.drawable.grapes, "Fruits", 2.50),
    Product("Pineapple", R.drawable.pineapple, "Fruits", 3.00)
)

val meatList = listOf(
    Product("Beef", R.drawable.beef, "Meats", 10.00),
    Product("Chicken", R.drawable.chicken, "Meats", 5.00),
    Product("Pork", R.drawable.pork, "Meats", 7.00),
)

val fishList = listOf(
    Product("Tilapia", R.drawable.tilipia, "Fish", 4.00),
    Product("Bangus", R.drawable.bangus, "Fish", 10.00)
)

val spiceList = listOf(
    Product("Pepper", R.drawable.pepper, "Spices", 2.00),
    Product("Salt", R.drawable.slat, "Spices", 1.00),
    Product("Paprika", R.drawable.paprika, "Spices", 3.00),

    )

val frozenFoodList = listOf(
    Product("Tender Juicy Hotdog", R.drawable.hotdog, "Frozen Foods", 5.00),
    Product("Ice Cream", R.drawable.icecream, "Frozen Foods", 3.50),
    Product("Frozen Peas", R.drawable.frozen_peas, "Frozen Foods", 2.00),
    Product("Chicken Nuggets", R.drawable.nuggets, "Frozen Foods", 4.50)
)
@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    val navController = rememberNavController()
    val cartViewModel = CartViewModel()
    val productName = "Apple"

    ProductScreen(
        navController = navController,
        cartViewModel = cartViewModel,
        productName = productName
    )
}
@Composable
fun ProductScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    productName: String?
) {
    // Combine all product lists for lookup
    val allProducts = vegetableList + fruitList + meatList + fishList + spiceList + frozenFoodList
    val product = allProducts.find { it.name == productName } ?: return // Exit if product not found

    val context = LocalContext.current
    var quantity by remember { mutableStateOf(1) }
    var selectedWeight by remember { mutableStateOf("1 pc") }
    val basePrice = product.price // Use product's base price
    val priceIncrease = 30.0 // Same increment as AppleScreen; adjust per product if needed

    // Calculate price based on selected weight
    val priceForWeight = when (selectedWeight) {
        "1 pc" -> basePrice
        "1/4 kg" -> basePrice + priceIncrease
        "1/2 kg" -> basePrice + priceIncrease
        "1 kg" -> basePrice + priceIncrease * 2
        else -> basePrice
    }
    val totalPrice = priceForWeight * quantity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Image Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentScale = ContentScale.Crop
            )


            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 70.dp, start = 40.dp)
                    .align(Alignment.TopStart)
                    .size(20.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Product Details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = product.name,
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

                // Quantity Buttons
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
                        text = "Martha Rosario (Aling Martha’s)",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "0900-000-0000",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "123 Street, Dagupan City",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            // Weight Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("1 pc", "1/4 kg", "1/2 kg", "1 kg").forEach { option ->
                    Button(
                        modifier = Modifier
                            .height(55.dp)
                            .width(85.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(10.dp)),
                        onClick = { selectedWeight = option },
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
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Bottom Bar with Total Price and Add to Basket
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

            Button(
                onClick = {
                    val cartItem = CartItem(
                        id = "${product.name}-${selectedWeight}-${System.currentTimeMillis()}",
                        name = product.name,
                        weight = selectedWeight,
                        quantity = quantity,
                        price = priceForWeight,
                        imageResId = product.imageRes
                    )
                    cartViewModel.addToCart(cartItem)
                    Toast.makeText(context, "Added to Basket!", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .height(58.dp)
                    .width(205.dp)
            ) {
                Text(
                    text = "Add to Basket",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}