package com.example.splashscreenbaskit.AccountDetails

import User
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controller.LogoutController
import com.example.splashscreenbaskit.controller.UserController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun AccountActivityPreview() {
    AccountActivity(navController = rememberNavController())
}

@Composable
fun AccountActivity(navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val userController = remember { UserController(lifecycleOwner, context) }
    val logoutController = remember { LogoutController(lifecycleOwner, context) }

    var user by remember { mutableStateOf<User?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var userRole by remember { mutableStateOf<String?>(null) }
    val showDialog = remember { mutableStateOf(false) }

    // Fetch user details when the screen loads
    LaunchedEffect(Unit) {
        userController.fetchUserDetails { success, error, fetchedUser ->
            if (success) {
                user = fetchedUser
                userRole = fetchedUser?.role
            } else {
                errorMessage = error
            }
        }
    }

    errorMessage?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    Log.d("ButtonClick", "Button pressed with userRole: $userRole")
                    if (userRole == "Consumer") {
                        navController.navigate("RulesScreen"){
                            popUpTo("AccountActivity") { inclusive = true}
                        }
                    } else if (userRole == "Seller") {
                        navController.navigate("EditStoreScreen") {
                            popUpTo("AccountActivity") { inclusive = true}
                        }
                    }else{
                        Log.d("ButtonClick", "Unexpected userRole: $userRole")
                        Toast.makeText(context, "Error: Unknown role", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5), contentColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth(0.38f)
                    .offset(x = (-10).dp)
                    .width(110.dp)
                    .height(35.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shop),
                    contentDescription = "Shop Icon",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Change text based on role
                Text(
                    text = if (userRole == "Consumer") "Start Selling >" else "My Store",
                    fontFamily = poppinsFontFamily,
                    fontSize = 10.sp
                )
            }

            IconButton(
                onClick = { navController.navigate("NotificationsActivity") },
                enabled = true,
                modifier = Modifier.padding(start = 120.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { navController.navigate("SettingsActivity") },
                enabled = true
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.account_img),
            contentDescription = null,
            modifier = Modifier
                .height(177.dp)
                .padding(start = 20.dp)
                .offset(x = 75.dp, y = (100).dp)
        )

        // Account details section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 300.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Account\ndetails",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Display User Details Dynamically
            AccountInfoRow("Name", Icons.Default.Person, user?.name ?: "Loading...", user?.role ?: "")
            Spacer(modifier = Modifier.height(10.dp))
            AccountInfoRows("Username", Icons.Default.Person, user?.username ?: "Loading...")
            Spacer(modifier = Modifier.height(10.dp))
            AccountInfoRows("Email", Icons.Default.Email, user?.email ?: "Loading...")
            Spacer(modifier = Modifier.height(10.dp))
            AccountInfoRows("Contact Number", Icons.Default.Phone, user?.contactNumber ?: "Loading...")
            Spacer(modifier = Modifier.height(10.dp))
            AccountInfoRows("Password", Icons.Default.Lock, "********")

            // Reset Password Section
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Reset password",
                    fontSize = 12.sp,
                    color = Color(0xFF4557FF),
                    fontFamily = poppinsFontFamily,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {
                        navController.navigate("ResetPasswordScreen")
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Log Out button
            Button(
                onClick = { showDialog.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(47.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFFE22727)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFFE22727))
            ) {
                Text(text = "Log Out", fontWeight = FontWeight.Bold, fontFamily = poppinsFontFamily)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // Confirmation Dialog
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Log Out?", fontSize = 18.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to log out?", fontSize = 14.sp, fontFamily = poppinsFontFamily) },
            confirmButton = {
                Button(
                    onClick = {
                        logoutController.logout { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            if (success) {
                                navController.navigate("LoginActivity") {
                                    popUpTo("AccountActivity") { inclusive = true } }
                            }
                        }
                        showDialog.value = false
                    }
                ) { Text("Log Out", fontSize = 15.sp, fontFamily = poppinsFontFamily) }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) { Text("Cancel") }
            }
        )
    }
}


@Composable
fun AccountInfoRow(label: String, icon: ImageVector, value: String, userRole: String) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            fontFamily = poppinsFontFamily
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F4DE), RoundedCornerShape(10.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(15.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
                Text(text = value, fontFamily = poppinsFontFamily)


                if (userRole == "Seller"){
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.verified),
                        contentDescription = "Seller",
                        modifier = Modifier.height(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AccountInfoRows(label: String, icon: ImageVector, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            fontFamily = poppinsFontFamily
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F4DE), RoundedCornerShape(10.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(15.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
                Text(text = value, fontFamily = poppinsFontFamily)
            }
        }
    }
}
