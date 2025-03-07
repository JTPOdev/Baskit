package com.example.splashscreenbaskit.Home

import android.util.Log
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.splashscreenbaskit.AccountDetails.AccountActivity
import com.example.splashscreenbaskit.AccountDetails.NotificationsActivity
import com.example.splashscreenbaskit.AccountDetails.SettingsActivity
import com.example.splashscreenbaskit.Carts.CartScreen
import com.example.splashscreenbaskit.Carts.CheckoutScreen
import com.example.splashscreenbaskit.LoginSignup.LoginActivity
import com.example.splashscreenbaskit.Products.ProductScreen
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import com.example.splashscreenbaskit.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import com.example.splashscreenbaskit.AccountDetails.EditStore
import com.example.splashscreenbaskit.AccountDetails.RequestSentScreen
import com.example.splashscreenbaskit.AccountDetails.RulesScreen
import com.example.splashscreenbaskit.AccountDetails.StoreRequestScreen
import com.example.splashscreenbaskit.LoginSignup.ChangePasswordScreen
import com.example.splashscreenbaskit.LoginSignup.EnterOTPScreen
import com.example.splashscreenbaskit.LoginSignup.ForgotPasswordScreen
import com.example.splashscreenbaskit.LoginSignup.ResetPasswordScreen

data class Vendor(val name: String, val imageRes: Int)
data class Product(val name: String, val imageRes: Int, val category: String, val price: Double = 0.0)

val dagupanVendors = listOf(
    Vendor("Aling Kristine's Shop", R.drawable.vendor1),
    Vendor("Green Store", R.drawable.vendors3),
    Vendor("Dagupan Market", R.drawable.food),
    Vendor("Prime Goods", R.drawable.food)
)

val calasiaoVendors = listOf(
    Vendor("Calasiao Fresh", R.drawable.food),
    Vendor("Golden Harvest", R.drawable.food),
    Vendor("Market Plus", R.drawable.food),
    Vendor("Sunny Farms", R.drawable.food)
)

val vegetableList = listOf(
    Product("Carrot", R.drawable.carrot, "Vegetables", 20.50),
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
    Product("Pork", R.drawable.pork, "Meats", 7.00)
)

val fishList = listOf(
    Product("Tilapia", R.drawable.tilipia, "Fish", 120.00),
    Product("Bangus", R.drawable.bangus, "Fish", 140.00)
)

val spiceList = listOf(
    Product("Pepper", R.drawable.pepper, "Spices", 2.00),
    Product("Salt", R.drawable.pepper, "Spices", 1.00),
    Product("Paprika", R.drawable.paprika, "Spices", 3.00),
    Product("Cinnamon", R.drawable.paprika, "Spices", 4.00)
)

val frozenFoodList = listOf(
    Product("Pizza", R.drawable.icecream, "Frozen Foods", 5.00),
    Product("Ice Cream", R.drawable.icecream, "Frozen Foods", 3.50),
    Product("Frozen Peas", R.drawable.frozen_peas, "Frozen Foods", 2.00),
    Product("Chicken Nuggets", R.drawable.nuggets, "Frozen Foods", 4.50)
)


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
                }
                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun VendorGrid(products: List<Vendor>, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        products.forEach { vendor ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(200.dp)
                    .clickable {
                        navController.navigate("ShopScreen/${vendor.name}/${vendor.imageRes}")
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = vendor.imageRes),
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
fun LabelChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFFFA726), shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
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
            enabled = true,
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

            SlideImg(modifier = Modifier.fillMaxSize().clipToBounds())

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
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontFamily = poppinsFontFamily)
            )
        }
        TextButton(onClick = { selectedLocation.value = "Calasiao" }) {
            Text(
                "CALASIAO",
                color = if (selectedLocation.value == "Calasiao") Color.Black else Color(0xFFBFBFBF),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontFamily = poppinsFontFamily)
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
fun ShopScreen(navController: NavController, vendorName: String, imageRes: Int) {
    // State for selected category
    val selectedCategory = remember { mutableStateOf<String?>("Vegetables") } // Default to "Vegetables"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header with Store Image and Back Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
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

        // Content Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Display Store Name
            Text(
                text = "$vendorName's Store",
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Category Row
            CategoryRow(
                selectedCategory = selectedCategory,
                navController = navController
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display Products Based on Selected Category
            when (selectedCategory.value) {
                "Vegetables" -> ProductGrid(products = vegetableList, navController = navController)
                "Fruits" -> ProductGrid(products = fruitList, navController = navController)
                "Meats" -> ProductGrid(products = meatList, navController = navController)
                "Fish" -> ProductGrid(products = fishList, navController = navController)
                "Spices" -> ProductGrid(products = spiceList, navController = navController)
                "Frozen Foods" -> ProductGrid(products = frozenFoodList, navController = navController)
                else -> {
                    Text(
                        text = "Select a category to view products",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
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
            if (currentRoute !in listOf("ProductScreen/{productName}", "CartScreen",
                    "CheckoutScreen", "ShopScreen/{vendorName}/{imageRes}", "StoreRequestScreen",
                "EditStore", "RulesScreen", "RequestSentScreen", "LoginActivity")) {
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
                    HomeContent(navController)
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
                    ProductScreen(
                        navController = navController,
                        cartViewModel = cartViewModel,
                        productName = backStackEntry.arguments?.getString("productName")
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
                composable("StoreRequestScreen") {
                    StoreRequestScreen(navController)
                }
                composable("EditStore") {
                    EditStore(navController)
                }
                composable("RulesScreen") {
                    RulesScreen(navController)
                }
                composable("AccountActivity"){
                    AccountActivity(navController)
                }
                composable(
                    "ShopScreen/{vendorName}/{imageRes}",
                    arguments = listOf(
                        navArgument("vendorName") { type = NavType.StringType },
                        navArgument("imageRes") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    ShopScreen(
                        navController = navController,
                        vendorName = backStackEntry.arguments?.getString("vendorName") ?: "",
                        imageRes = backStackEntry.arguments?.getInt("imageRes") ?: 0
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val selectedLocation = remember { mutableStateOf<String?>("Dagupan") }
    val scrollState = rememberScrollState()

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

            when (selectedCategory.value) {
                null, "STORE" -> {
                    when (selectedLocation.value) {
                        "Dagupan" -> VendorGrid(products = dagupanVendors, navController = navController)
                        "Calasiao" -> VendorGrid(products = calasiaoVendors, navController = navController)
                    }
                }
                "Vegetables" -> ProductGrid(products = vegetableList, navController = navController)
                "Fruits" -> ProductGrid(products = fruitList, navController = navController)
                "Meats" -> ProductGrid(products = meatList, navController = navController)
                "Fish" -> ProductGrid(products = fishList, navController = navController)
                "Spices" -> ProductGrid(products = spiceList, navController = navController)
                "Frozen Foods" -> ProductGrid(products = frozenFoodList, navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeActivity() {
    HomeScreen()
}
