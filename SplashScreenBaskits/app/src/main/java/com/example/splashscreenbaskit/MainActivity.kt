package com.example.splashscreenbaskit

import EditStoreScreen
import Order
import ProductsResponse
import StoreScreen
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.splashscreenbaskit.AccountDetails.AccountActivity
import com.example.splashscreenbaskit.AccountDetails.AddProductTest
import com.example.splashscreenbaskit.AccountDetails.NotificationsActivity
import com.example.splashscreenbaskit.AccountDetails.ProductDisplayScreen
import com.example.splashscreenbaskit.AccountDetails.RequestSentScreen
import com.example.splashscreenbaskit.AccountDetails.RulesScreen
import com.example.splashscreenbaskit.AccountDetails.SettingsActivity
import com.example.splashscreenbaskit.AccountDetails.StoreRequestScreen
import com.example.splashscreenbaskit.AccountDetails.TB_AccountDetails
import com.example.splashscreenbaskit.Carts.CartScreen
import com.example.splashscreenbaskit.Carts.CheckoutScreen
import com.example.splashscreenbaskit.Home.HomeScreen
import com.example.splashscreenbaskit.LoginSignup.ChangePasswordScreen
import com.example.splashscreenbaskit.LoginSignup.EnterOTPScreen
import com.example.splashscreenbaskit.LoginSignup.ForgotPasswordScreen
import com.example.splashscreenbaskit.LoginSignup.LoginActivity
import com.example.splashscreenbaskit.LoginSignup.OnboardingScreen
import com.example.splashscreenbaskit.LoginSignup.ResetPasswordScreen
import com.example.splashscreenbaskit.LoginSignup.SignUpActivity
import com.example.splashscreenbaskit.Products.ProductScreen
import com.example.splashscreenbaskit.Tagabili.TB_HomeContent
import com.example.splashscreenbaskit.Tagabili.TB_OrdersContent
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import com.example.splashscreenbaskit.controller.CartController
import com.example.splashscreenbaskit.ui.theme.SplashScreenBaskitTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi", "ComposableDestinationInComposeScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            SplashScreenBaskitTheme {

                TokenManager.init(this)

                val navController = rememberNavController()
                val context = LocalContext.current

                val sharedPreferences = context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)
                val hasSeenOnboarding = sharedPreferences.getBoolean("hasSeenOnboarding", false)
                val userPrefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val role = userPrefs.getString("auth_role", null)
                val isLoggedIn = TokenManager.getToken() != null
                val token = TokenManager.getToken()

                println("Token retrieved: $token")
                println("Role retrieved: $role")

                val startDestination = when {
                    !hasSeenOnboarding -> "OnboardingScreen"
                    isLoggedIn -> when (role) {
                        "Consumer" -> "home"
                        "Tagabili" -> "TB_HomeActivity"
                        "Seller" -> "home"
                        else -> "LoginActivity"
                    }
                    else -> "LoginActivity"
                }

                println("Navigating to: $startDestination")

                NavHost(navController, startDestination = startDestination) {
                    composable("OnboardingScreen") {
                        OnboardingScreen(navController, context) }
                    composable("SignUpActivity") {
                        SignUpActivity(navController)
                    }
                    composable("LoginActivity") {
                        LoginActivity(navController)
                    }
                    composable("AccountActivity") {
                        AccountActivity(navController)
                    }
                    composable("home") {
                        HomeScreen()
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
                    composable("RulesScreen") {
                        RulesScreen(navController)
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
                    composable("ProductDisplayScreen") {
                        ProductDisplayScreen(navController)
                    }
                    composable("EditStoreScreen") {
                        EditStoreScreen(navController)
                    }
                    composable("CheckoutScreen") {
                        val context = LocalContext.current
                        val lifecycleOwner = LocalLifecycleOwner.current
                        val apiService = RetrofitInstance.create(ApiService::class.java)

                        val cartController = remember { CartController(lifecycleOwner, context, apiService) }
                        CheckoutScreen(cartController = cartController, navController = navController)
                    }

                    composable(
                        "tb_orders/{user_id}",
                        arguments = listOf(navArgument("user_id") { type = NavType.StringType })
                    ) { backStackEntry ->
                        var orders by remember { mutableStateOf<List<Order>>(emptyList()) }

                        val userId = backStackEntry.arguments?.getString("user_id")?.toIntOrNull() ?: 0

                        TB_OrdersContent(navController, userId)
                    }
                    composable("StoreScreen/{storeId}", arguments = listOf(navArgument("storeId") { type = NavType.IntType })) { backStackEntry ->
                        val storeId = backStackEntry.arguments?.getInt("storeId") ?: 0
                        StoreScreen(navController, storeId)
                    }
                }
            }
        }
    }
}
