import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controller.UserStoreController
import com.example.splashscreenbaskit.controllers.ProductController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditStoreScreenPreview() {
    EditStoreScreen(navController = rememberNavController())
}

@Composable
fun EditStoreScreen(navController: NavController) {
    var storeName by remember { mutableStateOf("Loading...") }
    var storeImage by remember { mutableStateOf("default_image") }
    val products = remember { mutableStateListOf<ProductsResponse>() }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val selectedCategory = remember { mutableStateOf("All") }

    val context = LocalContext.current
    val userStoreController = UserStoreController(LocalLifecycleOwner.current, context)
    val productController = ProductController(LocalLifecycleOwner.current, context)

    LaunchedEffect(Unit) {
        userStoreController.fetchStoreDetails { success, errorMessage, store ->
            if (success && store != null) {
                storeName = store.store_name
                storeImage = store.store_image ?: "default_image"
            } else {
                storeName = errorMessage ?: "Error loading store"
            }
        }

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

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        TopBar(navController)
        StoreHeader(storeName, storeImage)
        ProductList(products, selectedCategory, isLoading, errorMessage, navController)
    }
}

@Composable
fun TopBar(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(16.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Back",
            tint = Color.Black
        )
    }
}

@Composable
fun StoreHeader(storeName: String, storeImage: String) {
    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(storeImage.trim())
                .crossfade(true)
                .build(),
            contentDescription = "Store Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier.fillMaxWidth().height(100.dp).align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                )
        )
        Text(
            text = storeName,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp).align(Alignment.BottomStart),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily
        )
    }
}

@Composable
fun ProductList(
    products: List<ProductsResponse>,
    selectedCategory: MutableState<String>,
    isLoading: MutableState<Boolean>,
    errorMessage: MutableState<String?>,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val categories = listOf("All", "Fruits", "Vegetables", "Meats", "Spices", "Frozen Foods", "Fish")

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(categories) { category ->
                FilterChip(category, selectedCategory.value == category) { selectedCategory.value = category }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFFA52F))
                }
            }
            errorMessage.value != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = errorMessage.value ?: "Unknown error", color = Color.Red, fontSize = 16.sp)
                }
            }
            else -> {
                val filteredProducts = if (selectedCategory.value == "All") {
                    products
                } else {
                    val categoryMap = mapOf(
                        "Fruits" to "FRUITS",
                        "Vegetables" to "VEGETABLES",
                        "Meats" to "MEAT",
                        "Fish" to "FISH",
                        "Spices" to "SPICES",
                        "Frozen Foods" to "FROZEN"
                    )
                    products.filter { it.product_category == categoryMap[selectedCategory.value] }
                }

                // Add "Add a product" button to the product list
                val productListWithAdd = remember(filteredProducts) {
                    filteredProducts.toMutableList().apply {
                        add(ProductsResponse("Add a product", "", "", null)) // Use null for nullable fields
                    }
                }


                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(productListWithAdd.chunked(2)) { rowProducts ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowProducts.forEach { product ->
                                if (!product.product_image.isNullOrEmpty()) {
                                    ProductItem(
                                        modifier = Modifier.weight(1f),
                                        product = product,
                                        navController = navController
                                    )
                                } else {
                                    AddProductButton(
                                        modifier = Modifier.weight(1f),
                                        navController = navController
                                    )
                                }

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
                text = "₱${String.format("%.2f", product.product_price.toDoubleOrNull() ?: 0.0)}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AddProductButton(navController: NavController, modifier: Modifier = Modifier) {
    Button(
        onClick = { navController.navigate("AddProductTest") },
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF0F0F0),
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Add Product",
                modifier = Modifier.size(40.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Add a product",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}


