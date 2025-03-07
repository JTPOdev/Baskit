package com.example.splashscreenbaskit.AccountDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controller.UserStoreController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

//  Product data class
data class Product(
    val name: String,
    val imageRes: Int
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditStorePreview() {
    EditStore(navController = rememberNavController())
}

@Composable
fun EditStore(navController: NavController) {
    var storeName by remember { mutableStateOf("Loading...") } // Default to "Loading..."
    var storeImage by remember { mutableStateOf("default_image") }
    var categoryInput by remember { mutableStateOf("") }
    val categories = remember { mutableStateOf(mutableListOf<String>()) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var newProductName by remember { mutableStateOf("") }

    // Fetch store details
    val context = LocalContext.current
    val userStoreController = UserStoreController(LocalLifecycleOwner.current, context)

    LaunchedEffect(key1 = true) {
        userStoreController.fetchStoreDetails { success, errorMessage, store ->
            if (success && store != null) {
                storeName = store.store_name  // Update store name from API
                storeImage = store.store_image ?: "default_image"
            } else {
                storeName = errorMessage ?: "Error loading store"
                storeImage = "default_image"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(315.dp)
                .background(Color(0xFFDBDBDB)),
            contentAlignment = Alignment.BottomStart
        ) {
            if (storeImage == "default_image") {
                Image(
                    painter = painterResource(id = R.drawable.seller_img),
                    contentDescription = "Seller",
                    modifier = Modifier.fillMaxWidth().height(315.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = rememberImagePainter(storeImage),
                    contentDescription = "Seller Image",
                    modifier = Modifier.fillMaxWidth().height(315.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.BottomStart)
            ) {
                TextButton(onClick = { }) {
                    Text(
                        storeName,  // Dynamically updated store name
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontFamily = poppinsFontFamily)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 70.dp, start = 40.dp)
                    .align(Alignment.TopStart)
                    .size(15.dp)
                    .background(Color(0xAAFFFFFF), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        // Category Selection Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .clickable { },
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .background(
                        color = Color.LightGray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { }
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = selectedCategory ?: "Add a category",
                        color = if (selectedCategory == null) Color.Black else Color.Black,
                        fontSize = 16.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = if (selectedCategory == null) FontWeight.SemiBold else FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.add_category),
                        contentDescription = "Dropdown",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                    )
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFA52F)),
            modifier = Modifier
                .height(68.dp)
                .fillMaxWidth()
                .clickable { },
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(listOf("Fruits", "Vegetables", "Meat", "Spices", "Frozen Foods", "Fish")) { category ->
                        Text(
                            text = category,
                            fontSize = 16.sp,
                            fontWeight = if (selectedCategory == category) FontWeight.ExtraBold else FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            color = if (selectedCategory == category) Color.White else Color(0xFFCCCCCC),
                            modifier = Modifier
                                .clickable {
                                    selectedCategory = category
                                    if (!categories.value.contains(category)) {
                                        categories.value = categories.value.toMutableList().apply { add(category) }
                                    }
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

//        // Product Grid
//        ProductGridWithAddButton(
//            products = products,
//            newProductName = newProductName,
//            navController = navController,
//            modifier = Modifier
//                .weight(1f)
//                .padding(top = 10.dp)
//        )
    }
}

@Composable
fun ProductGridWithAddButton(
    products: List<Product>,
    newProductName: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val productListWithAdd = remember(products) {
        products.toMutableList().apply {
            add(Product("Add a product", -1))
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        productListWithAdd.chunked(2).forEach { rowProducts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowProducts.forEach { product ->
                    if (product.imageRes != -1) {
                        // Regular product card
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                            modifier = Modifier
                                .weight(1f)
                                .height(170.dp)
                                .width(154.dp)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    navController.navigate("ProductScreen/${product.name}")
                                },
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = product.imageRes),
                                    contentDescription = "Product Image",
                                    modifier = Modifier
                                        .height(100.dp)
                                        .width(135.dp)
                                        .padding(top = 8.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = product.name,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    fontFamily = poppinsFontFamily,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    } else {
                        // Add product card
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                            modifier = Modifier
                                .weight(1f)
                                .height(170.dp)
                                .width(154.dp)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    if (newProductName.isNotEmpty()) {
                                        navController.navigate("ProductScreen/$newProductName")
                                    }
                                },
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = "Add Product",
                                        tint = Color.Black,
                                        modifier = Modifier.size(35.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = product.name,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        fontFamily = poppinsFontFamily,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

