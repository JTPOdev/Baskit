package com.example.splashscreenbaskit.AccountDetails

import ProductsResponse
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.splashscreenbaskit.controllers.ProductController

@Composable
fun ProductDisplayScreen(navController: NavController) {
    val products = remember { mutableStateListOf<ProductsResponse>() }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val predefinedCategories = listOf("All", "Fruits", "Vegetables", "Meats", "Spices", "Frozen Foods", "Fish")
    var selectedCategory by remember { mutableStateOf("All") }

    val context = LocalContext.current
    val productController = ProductController(LocalLifecycleOwner.current, context)

    LaunchedEffect(Unit) {
        productController.fetchProductDetails { success, message, productList ->
            isLoading.value = false
            if (success && productList != null) {
                products.clear()
                products.addAll(productList)
            } else {
                errorMessage.value = message
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(predefinedCategories) { category ->
                FilterChip(
                    text = category,
                    isSelected = selectedCategory == category,
                    onClick = { selectedCategory = category }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading.value -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFFFFA52F)
                    )
                }
                errorMessage.value != null -> {
                    Text(
                        text = errorMessage.value ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp
                    )
                }
                else -> {
                    val categoryMap = mapOf(
                        "Fruits" to "FRUITS",
                        "Vegetables" to "VEGETABLES",
                        "Meats" to "MEAT",
                        "Fish" to "FISH",
                        "Spices" to "SPICES",
                        "Frozen Foods" to "FROZEN"
                    )

                    val filteredProducts = if (selectedCategory == "All") {
                        products
                    } else {
                        val databaseCategory = categoryMap[selectedCategory] ?: selectedCategory
                        products.filter { it.product_category == databaseCategory }
                    }
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredProducts.chunked(2)) { rowProducts ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                rowProducts.forEach { product ->
                                    ProductItem(product, navController, modifier = Modifier.weight(1f))
                                }
                                if (rowProducts.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFFFA52F) else Color.LightGray,
            contentColor = Color.White
        ),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}

@Composable
fun ProductItem(product: ProductsResponse, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                navController.navigate("ProductScreen/${product.product_name}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.product_image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.product_name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "₱${String.format("%.2f", product.product_price)}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}