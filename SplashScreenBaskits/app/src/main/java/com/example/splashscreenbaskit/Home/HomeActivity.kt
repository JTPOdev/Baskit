package com.example.splashscreenbaskit.Home

import CartItem
import EditStoreScreen
import Order
import ProductList
import ProductsResponse
import StoreHeader
import StoreResponse
import StoreScreen
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.splashscreenbaskit.AccountDetails.*
import com.example.splashscreenbaskit.Carts.CartScreen
import com.example.splashscreenbaskit.Carts.CheckoutScreen
import com.example.splashscreenbaskit.LoginSignup.*
import com.example.splashscreenbaskit.Products.ProductScreen
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.Tagabili.TB_HomeContent
import com.example.splashscreenbaskit.Tagabili.TB_OrdersContent
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import com.example.splashscreenbaskit.controller.CartController
import com.example.splashscreenbaskit.controller.UserStoreController
import com.example.splashscreenbaskit.controllers.ProductByOriginController
import com.example.splashscreenbaskit.controllers.ProductController
import com.example.splashscreenbaskit.controllers.StoreByOriginController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


// Data Models
data class Vendor(
    val id: Int,
    val name: String,
    val imageUrl: String
)

// UI Components
@Composable
fun CategoryRow(selectedCategory: MutableState<String?>, navController: NavController) {
    val categories = listOf("Stores", "Fruits", "Vegetables", "Meats", "Spices", "Frozen Foods", "Fish")
    val listState = rememberLazyListState()

    LaunchedEffect(selectedCategory.value) {
        val index = categories.indexOf(selectedCategory.value)
        if (index != -1) {
            listState.animateScrollToItem(index, scrollOffset = -350)
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 8.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (selectedCategory.value == category) Color(0xFFFFA726) else Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        selectedCategory.value = category
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = category,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    textAlign = TextAlign.Center,
                    color = if (selectedCategory.value == category) Color.White else Color(0xFFBFBFBF)
                )
            }
        }
    }
}

@Composable
fun SearchBar(navController: NavController) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .height(50.dp)
                .weight(1f)
                .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth()) {
                    if (searchText.text.isEmpty()) {
                        Text(
                            text = "Search food, vegetable, etc.",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily
                        )
                    }
                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            onClick = { navController.navigate("NotificationsActivity") },
            modifier = Modifier.size(25.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun SliderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(149.dp)
            .padding(start = 30.dp, end = 30.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SlideImg(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun LocationSelector(selectedLocation: MutableState<String?>) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        TextButton(onClick = { selectedLocation.value = "Dagupan" }) {
            Text(
                "DAGUPAN",
                color = if (selectedLocation.value == "Dagupan") Color.Black else Color(0xFFBFBFBF),
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )
        }
        TextButton(onClick = { selectedLocation.value = "Calasiao" }) {
            Text(
                "CALASIAO",
                color = if (selectedLocation.value == "Calasiao") Color.Black else Color(0xFFBFBFBF),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(BottomBarScreen.Home, BottomBarScreen.Cart, BottomBarScreen.Account)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var selectedPosition by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Color(0xFF1d7151)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(top = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEachIndexed { index, screen ->
                val isSelected = currentDestination?.route == screen.route
                val iconSize by animateDpAsState(targetValue = if (isSelected) 34.dp else 26.dp, label = "Icon Size Animation")
                val iconColor by animateColorAsState(targetValue = if (isSelected) Color.White else Color.Gray, label = "Icon Color Animation")

                BottomNavigationItem(
                    label = {
                        Text(
                            text = screen.title,
                            fontFamily = poppinsFontFamily,
                            color = if (isSelected) Color.White else Color.Gray
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title,
                            tint = if (isSelected) Color.White else Color.Gray,
                            modifier = Modifier.size(iconSize)
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        selectedPosition = index
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

sealed class BottomBarScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomBarScreen("home", "Home", Icons.Default.Home)
    object Cart : BottomBarScreen("cart", "My Basket", Icons.Default.ShoppingBasket)
    object Account : BottomBarScreen("account", "Account", Icons.Default.AccountCircle)
}

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Log.d("HomeScreen", "Current Route: $currentRoute")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute !in listOf("ProductScreen/{productName}/{productResponse}",
                    "CartScreen", "CheckoutScreen", "ShopScreen/{vendorName}/{vendorId}",
                    "StoreRequestScreen", "RulesScreen", "EditStoreScreen", "RequestSentScreen",
                    "AddProductTest", "ProductScreen", "StoreScreen", "LoginActivity",
                    "SignUpActivity", "TB_HomeActivity", "TB_OrdersActivity", "TB_AccountDetails" )) {
                BottomBar(navController = navController)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomBarScreen.Home.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(BottomBarScreen.Home.route) {
                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val productByOriginController = remember { ProductByOriginController(lifecycleOwner, context) }
                    val storeByOriginController = remember { StoreByOriginController(lifecycleOwner, context) }

                    HomeContent(
                        navController = navController,
                        productByOriginController = productByOriginController,
                        storeByOriginController = storeByOriginController
                    )
                }
                composable(BottomBarScreen.Cart.route) {
                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val apiService = RetrofitInstance.create(ApiService::class.java)

                    val cartController = remember { CartController(lifecycleOwner, context, apiService) }
                    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
                    //var isLoading by remember {mutableStateOf(true)}

                    LaunchedEffect(Unit) {
                        cartController.fetchCartItems { success, _, items ->
                            if (success) {
                                //isLoading = false
                                cartItems = items ?: emptyList()

                                val hasOrderPlaced =
                                    cartItems.any { it.order_status == "Order Placed" }
                                if (hasOrderPlaced) {
                                    navController.navigate("CheckoutScreen") {
                                        popUpTo(BottomBarScreen.Cart.route) { inclusive = true }
                                    }
                                }
                            }
                        }
                    }
//                    if (isLoading) {
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            CircularProgressIndicator()
//                        }
//                    } else {
                        CartScreen(cartController = cartController, navController = navController)
//                    }
                }
                composable(BottomBarScreen.Account.route) {
                    AccountActivity(navController)
                }
                composable(
                    "ProductScreen/{productName}/{productResponse}",
                    arguments = listOf(
                        navArgument("productName") { type = NavType.StringType },
                        navArgument("productResponse") { type = NavType.StringType }
                    )
                ) { backStackEntry ->

                    val apiService = RetrofitInstance.create(ApiService::class.java)
                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val cartController = remember { CartController(lifecycleOwner, context, apiService) }

                    val productName = backStackEntry.arguments?.getString("productName") ?: ""
                    val productJson = backStackEntry.arguments?.getString("productResponse") ?: ""
                    val productResponse = Gson().fromJson(productJson, ProductsResponse::class.java)

                    ProductScreen(
                        navController = navController,
                        cartController = cartController,
                        productName = productName,
                        productsResponse = productResponse
                    )
                }

                composable("CartScreen") {
                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val apiService = RetrofitInstance.create(ApiService::class.java)

                    val cartController = remember { CartController(lifecycleOwner, context, apiService) }

                    CartScreen(cartController = cartController, navController = navController)
                }
                composable("LoginActivity") {
                    LoginActivity(navController)
                }
                composable("SignUpActivity") {
                    SignUpActivity(navController)
                }
                composable("NotificationsActivity") {
                    NotificationsActivity(navController)
                }
                composable("SettingsActivity") {
                    SettingsActivity(navController)
                }
                composable("ForgotPasswordScreen") {
                    ForgotPasswordScreen(navController)
                }
                composable("EnterOTPScreen") {
                    EnterOTPScreen(navController)
                }
                composable("ChangePasswordScreen") {
                    ChangePasswordScreen(navController)
                }
                composable("ResetPasswordScreen") {
                    ResetPasswordScreen(navController)
                }
                composable("RequestSentScreen") {
                    RequestSentScreen(navController)
                }
                composable("RulesScreen") {
                    RulesScreen(navController)
                }
                composable("EditStoreScreen") {
                    EditStoreScreen(navController)
                }
                composable("AddProductTest") {
                    AddProductTest(navController)
                }
                composable("TB_HomeActivity") {
                    TB_HomeContent(navController)
                }
                composable("TB_OrdersActivity/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
                    TB_OrdersContent(navController, userId)
                }
                composable("TB_AccountDetails") {
                    TB_AccountDetails(navController)
                }
                composable(
                    "tb_orders/{user_id}",
                    arguments = listOf(navArgument("user_id") { type = NavType.StringType })
                ) { backStackEntry ->
                    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }

                    val userId = backStackEntry.arguments?.getString("user_id")?.toIntOrNull() ?: 0

                    TB_OrdersContent(navController, userId)
                }
                composable("StoreRequestScreen") {
                    StoreRequestScreen(navController)
                }
                composable("AccountActivity") {
                    AccountActivity(navController)
                }
                composable("CheckoutScreen") {
                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val apiService = RetrofitInstance.create(ApiService::class.java)

                    val cartController = remember { CartController(lifecycleOwner, context, apiService) }
                    CheckoutScreen(cartController = cartController, navController = navController)
                }
                composable("StoreScreen/{storeId}", arguments = listOf(navArgument("storeId") { type = NavType.IntType })) { backStackEntry ->
                    val storeId = backStackEntry.arguments?.getInt("storeId") ?: 0
                    StoreScreen(navController, storeId)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    navController: NavController,
    productByOriginController: ProductByOriginController,
    storeByOriginController: StoreByOriginController
) {

    val selectedCategory = remember { mutableStateOf<String?>("Stores") }
    val selectedLocation = remember { mutableStateOf<String?>("Dagupan") }

    val allProducts = remember { mutableStateListOf<ProductsResponse>() }
    val filteredProducts = remember { mutableStateListOf<ProductsResponse>() }
    val stores = remember { mutableStateListOf<StoreResponse>() }

    val isLoading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val isStoreLoading = remember { mutableStateOf(false) }
    val storeErrorMessage = remember { mutableStateOf<String?>(null) }

    val categoryMapping = mapOf(
        "Fruits" to "FRUITS", "Vegetables" to "VEGETABLES", "Meats" to "MEAT",
        "Spices" to "SPICES", "Frozen Foods" to "FROZEN", "Fish" to "FISH"
    )
    val locationMapping = mapOf("Dagupan" to "DAGUPAN", "Calasiao" to "CALASIAO")

//    val listState = rememberLazyListState()
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }


    LaunchedEffect(selectedLocation.value, selectedCategory.value) {
        val currentLocation = selectedLocation.value ?: return@LaunchedEffect

        if (selectedCategory.value == "Stores") {
            isStoreLoading.value = true
            storeErrorMessage.value = null
            stores.clear()
            filteredProducts.clear()
            allProducts.clear()

            val mappedLocation = locationMapping[currentLocation] ?: currentLocation.uppercase()
            storeByOriginController.fetchStoresByOrigin(mappedLocation) { success, message, storeList ->
                isStoreLoading.value = false
                stores.clear()
                filteredProducts.clear()
                allProducts.clear()

                if (success && storeList != null && storeList.isNotEmpty()) {
                    stores.addAll(storeList)
                } else {
                    storeErrorMessage.value = "No stores available"
                }
            }
        }
    }

    LaunchedEffect(selectedCategory.value, selectedLocation.value) {
        val currentCategory = selectedCategory.value ?: return@LaunchedEffect
        val currentLocation = selectedLocation.value ?: return@LaunchedEffect

        if (currentCategory == "Stores") return@LaunchedEffect

        isLoading.value = true
        errorMessage.value = null
        allProducts.clear()
        filteredProducts.clear()
        stores.clear()

        val mappedCategory = categoryMapping[currentCategory] ?: currentCategory.uppercase()
        val mappedLocation = locationMapping[currentLocation] ?: currentLocation.uppercase()

        productByOriginController.fetchProductsByOrigin(mappedCategory, mappedLocation) { success, message, productList ->
            isLoading.value = false
            stores.clear()
            filteredProducts.clear()
            allProducts.clear()
            if (success && productList != null) {
                allProducts.addAll(productList)
                filteredProducts.addAll(productList.filter { it.product_origin == mappedLocation })
            } else {
                errorMessage.value = message ?: "Failed to fetch products"
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.baskit_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "Shop Smarter, Not Harder",
                    fontFamily = poppinsFontFamily,
                    fontSize = 12.sp,
                    color = Color(0xFF1D7151),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.offset(y=-10.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                SearchBar(navController)
                Spacer(modifier = Modifier.height(30.dp))
                SliderCard()
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                LocationSelector(selectedLocation)
                Spacer(modifier = Modifier.height(1.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Gray.copy(alpha = 0.5f), Color.Transparent)
                                )
                            )
                    )
                }

                CategoryRow(selectedCategory, navController)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

        when {
            selectedCategory.value == "Stores" -> {
                if (isStoreLoading.value) {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFFFFA52F))
                        }
                    }
                } else if (!storeErrorMessage.value.isNullOrEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = storeErrorMessage.value ?: "Unknown error", color = Color.Red, fontSize = 14.sp, fontFamily = poppinsFontFamily)
                        }
                    }
                } else if (stores.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No stores available", color = Color.Gray, fontSize = 14.sp, fontFamily = poppinsFontFamily)
                        }
                    }
                } else {
                    item {
                        StoreGrid(stores, navController)
                    }
                }
            }
            isLoading.value -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFFFA52F))
                    }
                }
            }
            errorMessage.value != null -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = errorMessage.value ?: "Unknown error", color = Color.Red, fontSize = 14.sp, fontFamily = poppinsFontFamily)
                    }
                }
            }
            filteredProducts.isEmpty() -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No products available", color = Color.Gray, fontSize = 14.sp, fontFamily = poppinsFontFamily)
                    }
                }
            }
            else -> {
                items(filteredProducts.chunked(2)) { rowProducts ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowProducts.forEach { product ->
                            ProductItem(
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

@Composable
fun StoreGrid(stores: List<StoreResponse>, navController: NavController) {

    val sortedStores = stores.sortedWith(
        compareByDescending<StoreResponse> { it.store_status == "Partner" }
            .thenBy { it.id }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sortedStores.forEach { store ->
            StoreItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                store = store,
                navController = navController
            )
        }
    }
}

@Composable
fun StoreItem(modifier: Modifier = Modifier, store: StoreResponse, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                navController.navigate("StoreScreen/${store.id}")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = store.store_image,
                    contentDescription = store.store_name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )

                if (store.store_status == "Partner") {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .background(Color(0xFFFFA726), shape = RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Top Store",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = store.store_name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(top = 5.dp, end = 8.dp)
                )

                if (store.store_status == "Partner") {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFA726), shape = RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Recommended",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductsResponse, navController: NavController, modifier: Modifier = Modifier) {
    val productJson = Gson().toJson(product)
    val encodedProductName = URLEncoder.encode(product.product_name, StandardCharsets.UTF_8.toString())
    val encodedProductJson = URLEncoder.encode(productJson, StandardCharsets.UTF_8.toString())

    Card(
        modifier = modifier
            .height(180.dp)
            .width(154.dp)
            .padding(4.dp)
            .clickable {
                navController.navigate("ProductScreen/$encodedProductName/$encodedProductJson")
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
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
                    .height(100.dp)
                    .width(136.dp)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = product.product_name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "₱${String.format("%.2f", product.product_price.toDoubleOrNull() ?: 0.0)}",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeActivity() {
    HomeScreen()
}