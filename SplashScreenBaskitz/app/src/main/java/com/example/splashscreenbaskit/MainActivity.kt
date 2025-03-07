package com.example.splashscreenbaskit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.splashscreenbaskit.AccountDetails.AccountActivity
import com.example.splashscreenbaskit.AccountDetails.EditStore
import com.example.splashscreenbaskit.AccountDetails.NotificationsActivity
import com.example.splashscreenbaskit.AccountDetails.RequestSentScreen
import com.example.splashscreenbaskit.AccountDetails.RulesScreen
import com.example.splashscreenbaskit.AccountDetails.SettingsActivity
import com.example.splashscreenbaskit.AccountDetails.StoreRequestScreen
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
//import com.example.splashscreenbaskit.Products.AppleScreen
//import com.example.splashscreenbaskit.Products.BananaScreen
//import com.example.splashscreenbaskit.Products.GrapesScreen
//import com.example.splashscreenbaskit.Products.MangoScreen
//import com.example.splashscreenbaskit.Products.OrangeScreen
//import com.example.splashscreenbaskit.Products.PineappleScreen
import com.example.splashscreenbaskit.ui.theme.SplashScreenBaskitTheme
import com.example.splashscreenbaskit.viewmodel.CartViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi", "ComposableDestinationInComposeScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            SplashScreenBaskitTheme {
                val navController = rememberNavController()

                // Initialize CartViewModel here
                var cartViewModel: CartViewModel = viewModel()

                NavHost(navController = navController, startDestination = "OnBoardingScreen") {
                    composable("OnBoardingScreen") {
                        OnboardingScreen(navController)
                    }
                    composable("SignUpActivity") {
                        SignUpActivity(navController)
                    }
                    composable("LoginActivity") {
                        LoginActivity(navController)
                    }
                    composable("AccountActivity") {
                        AccountActivity(navController)
                    }
                    composable("HomeActivity") {
                        HomeScreen()
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
//                    composable("AppleScreen") {
//                        // Pass CartViewModel to AppleScreen
//                        AppleScreen(navController = navController, cartViewModel = cartViewModel)
//                    }
//                    composable("OrangeScreen") {
//                        OrangeScreen(navController = navController, cartViewModel = cartViewModel)
//                    }
//                    composable("BananaScreen") {
//                        BananaScreen(navController = navController, cartViewModel = cartViewModel)
//                    }
//                    composable("MangoScreen") {
//                        MangoScreen(navController = navController, cartViewModel = cartViewModel)
//                    }
//                    composable("GrapesScreen") {
//                        GrapesScreen(navController = navController, cartViewModel = cartViewModel)
//                    }
//                    composable("PineappleScreen") {
//                        PineappleScreen(navController = navController, cartViewModel = cartViewModel)
//                    }
                    composable("CartScreen") {
                        CartScreen(cartViewModel = cartViewModel, navController = navController)
                    }
                    composable("CheckoutScreen") {
                        CheckoutScreen(cartViewModel = cartViewModel, navController = navController)
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
                }
            }
        }
    }
}
