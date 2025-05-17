import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.splashscreenbaskit.AccountDetails.StoreRequestScreen
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.controller.UserStoreController
import com.example.splashscreenbaskit.controllers.ProductController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    val storeId = backStackEntry?.arguments?.getInt("storeId") ?: 0
    StoreScreen(navController, storeId)
}

@Composable
fun StoreScreen(navController: NavController, storeId: Int) {
    var storeName by remember { mutableStateOf("Loading...") }
    var storeImage by remember { mutableStateOf("default_image") }
    val products = remember { mutableStateListOf<ProductsResponse>() }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val selectedCategory = remember { mutableStateOf("") }


    val context = LocalContext.current
    val apiService = remember { RetrofitInstance.create(ApiService::class.java) }
    val userStoreController = UserStoreController(LocalLifecycleOwner.current, context, apiService)
    val productController = ProductController(LocalLifecycleOwner.current, context)

    LaunchedEffect(storeId) {
        userStoreController.fetchStoreDetailsUserID(storeId) { success, errorMessage, store ->
            if (success && store != null) {
                storeName = store.store_name
                storeImage = store.store_image ?: "default_image"
            } else {
                storeName = errorMessage ?: "Error loading store"
            }
        }

        productController.fetchProductDetailsByID(storeId) { success, message, productList ->
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
        StoreHeaders(storeName, storeImage, navController)
        ProductLists(products, selectedCategory, isLoading, navController, productController)
    }
}

@Composable
fun StoreHeaders(
    storeName: String,
    storeImage: String,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(315.dp)
            .background(Color(0xFFEBEBEB))
    ) {

        Image(
            painter = painterResource(id = R.drawable.inprogress),
            contentDescription = "orders",
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp)
                .offset(y = 10.dp)
        )

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 60.dp, start = 25.dp)
                .align(Alignment.TopStart)
                .size(40.dp)
                .zIndex(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = Color.White
            )
        }
        AsyncImage(
            model = if (storeImage.isNotBlank()) storeImage.trim() else R.drawable.vendor1,
            contentDescription = "Store Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                )
        )

        Text(
            text = storeName,
            modifier = Modifier
                .padding(start = 20.dp, bottom = 10.dp)
                .align(Alignment.BottomStart),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily
        )
    }
}

@Composable
fun ProductLists(
    products: List<ProductsResponse>,
    selectedCategory: MutableState<String>,
    isLoading: MutableState<Boolean>,
    navController: NavController,
    productController: ProductController
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        val categoryMap = mapOf(
            "FRUITS" to "Fruits",
            "VEGETABLES" to "Vegetables",
            "MEAT" to "Meats",
            "FISH" to "Fish",
            "SPICES" to "Spices",
            "FROZEN" to "Frozen Foods"
        )

//      val availableCategories = products.map { it.product_category }.distinct().mapNotNull { categoryMap[it] }
        val availableCategories by remember(products) {
            derivedStateOf {
                products.mapNotNull { categoryMap[it.product_category] }.distinct()
            }
        }
        LaunchedEffect(availableCategories) {
            if (availableCategories.isNotEmpty()) {
                selectedCategory.value = availableCategories.first()
            }
        }
        val listState = rememberLazyListState()

        LaunchedEffect(products) {
            if (products.isNotEmpty()) {
                val firstAvailableCategoryKey = products
                    .mapNotNull { it.product_category }
                    .distinct()
                    .firstOrNull()

                firstAvailableCategoryKey?.let { key ->
                    selectedCategory.value = categoryMap[key] ?: key
                }
            }
        }

        LaunchedEffect(selectedCategory.value) {
            val index = availableCategories.indexOf(selectedCategory.value)
            if (index != -1) {
                listState.animateScrollToItem(index, scrollOffset = -350)
            }
        }

        LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
            items(availableCategories) { category ->
                FilterChips(
                    text = category,
                    isSelected = selectedCategory.value == category
                ) {
                    selectedCategory.value = category
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        when {
            isLoading.value -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFFFA52F))
                }
            }
            else -> {
                val selectedCategoryKey = categoryMap.entries.find { it.value == selectedCategory.value }?.key
                val filteredProducts = products.filter { it.product_category == selectedCategoryKey }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(filteredProducts.chunked(2)) { rowProducts ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowProducts.forEach { product ->
                                ProductItems(
                                    modifier = Modifier.weight(1f),
                                    product = product,
                                    navController = navController
                                )
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
fun FilterChips(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFFFA52F) else Color.LightGray,
            contentColor = Color.White
        ),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductItems(product: ProductsResponse, navController: NavController, modifier: Modifier = Modifier) {
    val productJson = Gson().toJson(product)
    val encodedProductName = URLEncoder.encode(product.product_name, StandardCharsets.UTF_8.toString())
    val encodedProductJson = URLEncoder.encode(productJson, StandardCharsets.UTF_8.toString())

    Card(
        modifier = modifier
            .height(180.dp)
            .width(154.dp)
            .padding(5.dp)
            .clickable {
                navController.navigate("ProductScreen/$encodedProductName/$encodedProductJson")
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
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
                    .height(100.dp)
                    .width(140.dp)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.product_name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "₱${String.format("%.2f", product.product_price.toDoubleOrNull() ?: 0.0)}",
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}