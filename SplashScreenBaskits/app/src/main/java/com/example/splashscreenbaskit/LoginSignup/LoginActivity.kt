package com.example.splashscreenbaskit.LoginSignup

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.Home.BottomBarScreen
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controller.LoginController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun LoginActivity() {
    LoginActivity(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginActivity(navController: NavController) {
    var UsernameOrEmail = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    val options = listOf("Guest", "Tagabili")
    var selectedOption by remember { mutableStateOf(options[0]) }
    var showDialog by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    var showNewPasswordField by remember { mutableStateOf(false) }

    var NotVerifiedError = remember { mutableStateOf(("")) }
    var UsernameOrEmailError = remember { mutableStateOf("") }
    var PasswordError = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val loginController = remember { LoginController(lifecycleOwner, context) }
    var loginMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    //--------- COLORS ---------//
    val DarkGray = Color(0xFF48444C)
    val DarkBlue = Color(0xFF505C94)
    val Red = Color(0xFFF94B51)

    fun showUsernameOrEmailError(message: String) {
        UsernameOrEmailError.value = message
    }

    fun showPasswordError(message: String) {
        PasswordError.value = message
    }

    fun NotVerifiedError(message: String) {
        NotVerifiedError.value = message
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(Color.White),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(130.dp))

        Image(
            painter = painterResource(id = R.drawable.baskit_white),
            contentDescription = "Login Image",
            modifier = Modifier.width(230.dp) .height(200.dp)
        )

        Text(
            text = "Log into your account",
            color = Color.Gray,
            fontFamily = poppinsFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(bottom = 5.dp)
                .align(Alignment.CenterHorizontally),
        )

        OutlinedTextField(
            value = UsernameOrEmail.value,
            onValueChange = { UsernameOrEmail.value = it },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            label = {
                Text(
                    text = "Username or Email",
                    fontFamily = poppinsFontFamily,
                    color = if (UsernameOrEmailError.value.isNotEmpty() || NotVerifiedError.value.isNotEmpty()) Red else Color.Gray
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(10.dp),
            isError = UsernameOrEmailError.value.isNotEmpty(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = if (UsernameOrEmailError.value.isNotEmpty() || NotVerifiedError.value.isNotEmpty()) Red else DarkGray,
                focusedBorderColor = if (UsernameOrEmailError.value.isNotEmpty() || NotVerifiedError.value.isNotEmpty()) Red else Black,
                unfocusedBorderColor = if (UsernameOrEmailError.value.isNotEmpty() || NotVerifiedError.value.isNotEmpty()) Red else Color.Gray,
                errorBorderColor = Red,
                errorLabelColor = Red,
                errorLeadingIconColor = Red
            )
        )
        if (UsernameOrEmailError.value.isNotEmpty()) {
            Text(UsernameOrEmailError.value,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }else if (NotVerifiedError.value.isNotEmpty()){
            Text(NotVerifiedError.value,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            leadingIcon = { Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                        tint = if (PasswordError.value.isNotEmpty()) Red else Color.Gray
                    )
                }
            },
            label = {
                Text(
                    text = "Password",
                    fontFamily = poppinsFontFamily,
                    color = if (PasswordError.value.isNotEmpty()) Red else Color.Gray
                )
            },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(10.dp),
            isError = PasswordError.value.isNotEmpty(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = if (PasswordError.value.isNotEmpty()) Red else DarkGray,
                focusedBorderColor = if (PasswordError.value.isNotEmpty()) Red else Black,
                unfocusedBorderColor = if (PasswordError.value.isNotEmpty()) Red else Color.Gray,
                errorBorderColor = Red,
                errorLabelColor = Red,
                errorLeadingIconColor = Red
            )
        )
        if (PasswordError.value.isNotEmpty()) {
            Text(PasswordError.value, color = Color.Red, fontSize = 12.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal)
        }




        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot password?",
                fontFamily = poppinsFontFamily,
                fontSize = 12.sp,
                color = Color(0xFF4557FF),
                modifier = Modifier.clickable {
                    Log.d("ForgotPassword", "Clicked!")
                    showDialog = true
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                isLoading = true
                loginController.login(UsernameOrEmail.value, password.value) { success, message, role, errors ->
                    loginMessage = message
                    Toast.makeText(context, loginMessage, Toast.LENGTH_SHORT).show()
                    isLoading = false
                    if (success) {
                        when (role) {
                            "Consumer" -> navController.navigate("HomeActivity")
                            "Tagabili" -> navController.navigate("TB_HomeActivity")
                            "Seller" -> navController.navigate("HomeActivity"){
                                popUpTo("LoginActivity") { inclusive = true }
                            }
                            else -> Toast.makeText(context, "Unknown role: $role", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        UsernameOrEmailError.value = errors["username_or_email"] ?: ""
                        NotVerifiedError.value = errors["verification"] ?: ""
                        PasswordError.value = errors["password"] ?: ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
            enabled = UsernameOrEmail.value.isNotBlank() && password.value.isNotBlank(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1d7151),
                contentColor = Color.White
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Login", fontFamily = poppinsFontFamily)

                if (isLoading) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(150.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            TextButton(
                onClick = { navController.navigate("SignUpActivity") },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Sign Up", fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    color = Color(0xFF4557FF)
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Reset Password") },
                text = {
                    Column {
                        Text("Enter a new password")
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("New Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        showNewPasswordField = true
                        Log.d("ForgotPassword", "Password reset confirmed")
                    }) {
                        Text("Reset")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
