package com.example.splashscreenbaskit.LoginSignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.splashscreenbaskit.R
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun EnterOTPPreview() {
    EnterOTPScreen(navController = rememberNavController())
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterOTPScreen(navController: NavController) {
    var otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = List(otpValues.size) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val isOtpComplete = otpValues.all { it.isNotEmpty() }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "< Back",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            modifier = Modifier
                .padding(start = 20.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(70.dp))


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.otp_img),
                contentDescription = "Enter one-time pin",
                modifier = Modifier.height(181.dp),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Enter One-Time Pin",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Check your email for the code.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily,
                color = Color(0xFF8C8C8C)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in otpValues.indices) {
                    OutlinedTextField(
                        value = otpValues[i],
                        onValueChange = { value ->
                            if (value.length <= 1) {
                                otpValues[i] = value
                                if (value.isNotEmpty() && i < otpValues.lastIndex) {
                                    focusRequesters[i + 1].requestFocus()
                                }
                            }
                        },
                        textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .width(52.dp)
                            .height(57.dp)
                            .padding(4.dp)
                            .focusRequester(focusRequesters[i])
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Backspace && otpValues[i].isEmpty() && i > 0) {
                                    focusRequesters[i - 1].requestFocus()
                                }
                                false
                            },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("ChangePasswordScreen") },
                    enabled = isOtpComplete,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBDE0FE),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                }
            }
        }
    }
}

