package com.example.splashscreenbaskit.Home

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.splashscreenbaskit.AccountDetails.*
import com.example.splashscreenbaskit.Carts.CartScreen
import com.example.splashscreenbaskit.Carts.CheckoutScreen
import com.example.splashscreenbaskit.LoginSignup.*
import com.example.splashscreenbaskit.Products.ProductScreen
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import com.example.splashscreenbaskit.viewmodel.CartViewModel

// Data Models
data class Vendor(
    val id: Int,
    val name: String,
    val imageUrl: String
)

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val category: String,
    val price: Double
)

//ipaddress to palitan mo na alng
//307

// UI Components
@Composable
fun CategoryRow(selectedCategory: MutableState<String?>, navController: NavController) {
    val categories = listOf("STORE", "Vegetables", "Fruits", "Meats", "Fish", "Spices", "Frozen Foods")

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp, start = 8.dp, end = 10.dp)
    ) {
        items(categories) { category ->
            TextButton(
                modifier = Modifier
                    .background(
                        color = if (selectedCategory.value == category) Color(0xFFFFA726) else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .wrapContentHeight()
                    .heightIn(min = 20.dp),
                onClick = { selectedCategory.value = category }
            ) {
                Text(
                    text = category,
                    color = if (selectedCategory.value == category) Color(0xFFFFFFFF) else Color(0xFFBFBFBF),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ProductGrid(products: List<Product>, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        products.chunked(2).forEach { rowProducts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowProducts.forEach { product ->
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
                            AsyncImage(
                                model = product.imageUrl,
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
                }
                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun VendorGrid(vendors: List<Vendor>, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        vendors.forEach { vendor ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(200.dp)
                    .clickable {
                        navController.navigate("ShopScreen/${vendor.name}/${vendor.id}")
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = vendor.imageUrl,
                            contentDescription = vendor.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                            contentScale = ContentScale.Crop
                        )
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = vendor.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.padding(top = 5.dp, end = 8.dp)
                        )
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
            AsyncImage(
                model = "//api",
                contentDescription = "Slider Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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
    object Cart : BottomBarScreen("cart", "Cart", Icons.Default.ShoppingCart)
    object Account : BottomBarScreen("account", "Account", Icons.Default.AccountCircle)
}

@Composable
fun ShopScreen(navController: NavController, vendorName: String, vendorId: Int, viewModel: HomeViewModel = viewModel()) {
    val selectedCategory = remember { mutableStateOf<String?>("Vegetables") }

//    LaunchedEffect(selectedCategory.value) {
//        selectedCategory.value?.let { category ->
//            viewModel.fetchProducts(category)
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            val vendor = viewModel.dagupanVendors.find { it.id == vendorId }
                ?: viewModel.calasiaoVendors.find { it.id == vendorId }
            AsyncImage(
                model = vendor?.imageUrl ?: "",
                contentDescription = vendorName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "$vendorName's Store",
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            CategoryRow(selectedCategory = selectedCategory, navController = navController)
            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                ProductGrid(products = viewModel.products, navController = navController)
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val cartViewModel: CartViewModel = viewModel()

    Log.d("HomeScreen", "Current Route: $currentRoute")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute !in listOf("ProductScreen/{productName}", "CartScreen", "CheckoutScreen", "ShopScreen/{vendorName}/{vendorId}")) {
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
                    HomeContent(navController = navController)
                }
                composable(BottomBarScreen.Cart.route) {
                    CartScreen(cartViewModel = cartViewModel, navController = navController)
                }
                composable(BottomBarScreen.Account.route) {
                    AccountActivity(navController)
                }
                composable(
                    "ProductScreen/{productName}",
                    arguments = listOf(navArgument("productName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val productName = backStackEntry.arguments?.getString("productName")
                    ProductScreen(
                        navController = navController,
                        cartViewModel = cartViewModel,
                        productName = productName
                    )
                }
                composable("CartScreen") {
                    CartScreen(cartViewModel = cartViewModel, navController = navController)
                }
                composable("CheckoutScreen") {
                    CheckoutScreen(cartViewModel = cartViewModel, navController = navController)
                }
                composable("LoginActivity") {
                    LoginActivity(navController)
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
                composable(
                    "ShopScreen/{vendorName}/{vendorId}",
                    arguments = listOf(
                        navArgument("vendorName") { type = NavType.StringType },
                        navArgument("vendorId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    ShopScreen(
                        navController = navController,
                        vendorName = backStackEntry.arguments?.getString("vendorName") ?: "",
                        vendorId = backStackEntry.arguments?.getInt("vendorId") ?: 0
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val selectedLocation = remember { mutableStateOf<String?>("Dagupan") }
    val scrollState = rememberScrollState()

    LaunchedEffect(selectedLocation.value) {
        when (selectedLocation.value) {
            "Dagupan" -> viewModel.fetchDagupanVendors()
            "Calasiao" -> viewModel.fetchCalasiaoVendors()
        }
    }
//    LaunchedEffect(selectedCategory.value) {
//        selectedCategory.value?.let { category ->
//            if (category != "STORE") {
//                viewModel.fetchProducts(category)
//            }
//        }
//    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.baskit_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Shop Smarter, Not Harder",
                fontFamily = poppinsFontFamily,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(y = (-20).dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            SearchBar(navController)
            Spacer(modifier = Modifier.height(30.dp))
            SliderCard()
            Spacer(modifier = Modifier.height(15.dp))
            LocationSelector(selectedLocation)
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

            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                when (selectedCategory.value) {
                    null, "STORE" -> {
                        when (selectedLocation.value) {
                            "Dagupan" -> VendorGrid(vendors = viewModel.dagupanVendors, navController = navController)
                            "Calasiao" -> VendorGrid(vendors = viewModel.calasiaoVendors, navController = navController)
                        }
                    }
                    else -> ProductGrid(products = viewModel.products, navController = navController)
                }
            }
        }
    }
}