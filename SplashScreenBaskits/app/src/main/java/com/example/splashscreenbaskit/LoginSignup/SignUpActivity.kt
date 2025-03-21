package com.example.splashscreenbaskit.LoginSignup


import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.controller.RegisterController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun SignUpActivityPreview() {
    SignUpActivity(navController =  rememberNavController())
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpActivity(navController: NavController)
{
    //--------- REGISTER INPUT ---------//
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var userName = remember { mutableStateOf("") }
    var contactNumber = remember { mutableStateOf("") }
    var birthday = remember { mutableStateOf(TextFieldValue("")) }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var showTerms by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    //---------- ERROR HANDLER ---------//
    var birthdayError = remember { mutableStateOf("") }
    var userNameError = remember { mutableStateOf("") }
    var emailError = remember { mutableStateOf("") }
    var contactNumberError = remember { mutableStateOf("") }
    var passwordError = remember { mutableStateOf("") }
    var generalError = remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    //---------- LOADER HANDLER ---------//
    var isLoading by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }

    //---------- REGISTER CONTROLLER ---------//
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val registerController = RegisterController (lifecycleOwner, context)
    var registrationMessage by remember { mutableStateOf("") }
    var registrationSuccessful by remember { mutableStateOf(false) }

    //--------- COLORS ---------//
    val DarkGray = Color(0xFF48444C)
    val DarkBlue = Color(0xFF505C94)
    val Red = Color(0xFFF94B51)


    fun showBirthdayError(message: String) {
        birthdayError.value = message
    }

    fun showUsernameError(message: String) {
        userNameError.value = message
    }

    fun showEmailError(message: String) {
        emailError.value = message
    }

    fun showContactError(message: String) {
        contactNumberError.value = message
    }

    fun showPasswordError(message: String) {
        passwordError.value = message
    }

    fun showGeneralError(message: String) {
        generalError.value = message
    }

    fun validateInputs(): Boolean {
        var isValid = true

        confirmPasswordError = when {
            confirmPassword.isBlank() -> "Confirm your password"
            confirmPassword != password.value -> "Passwords do not match"
            else -> ""
        }
        return isValid
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))

            Image(
                painter = painterResource(id = R.drawable.baskit_logo),
                contentDescription = "Sign Up image",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Create your account",
                color = Color.Gray,
                fontFamily = poppinsFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Row for First Name and Last Name
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // First Name field
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { if (it.all { char -> char.isLetter() }) {
                        firstName = it
                    }
                    },
                    singleLine = true,
                    label = { Text(text = "First Name", fontFamily = poppinsFontFamily)},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = DarkGray,
                        focusedBorderColor = Black,
                        unfocusedBorderColor = Color.Gray,

                        )
                )

                // Last Name field
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { if (it.all { char -> char.isLetter() }) {
                        lastName = it
                    }
                    },
                    singleLine = true,
                    label = { Text(text = "Last Name", fontFamily = poppinsFontFamily) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = DarkGray,
                        focusedBorderColor = Black,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            OutlinedTextField(
                value = birthday.value,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.text.filter { it.isDigit() }
                    val formattedDate = buildString {
                        for (i in digitsOnly.indices) {
                            if (i == 2 || i == 4) append("/")
                            append(digitsOnly[i])
                        }
                    }

                    if (formattedDate.length <= 10) {//new
                        birthday.value = TextFieldValue(//new
                            text = formattedDate,//new
                            selection = TextRange(formattedDate.length)//new
                        )
                    }
                    birthdayError.value = ""},
                label = { Text("Birthday",
                    fontFamily = poppinsFontFamily,
                    color = if (birthdayError.value.isNotEmpty()) Red else Color.Gray) },
                placeholder = { Text("MM/DD/YYYY") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Calendar Icon",
                        tint = if (birthdayError.value.isNotEmpty()) Red else DarkGray
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                interactionSource = remember { MutableInteractionSource() },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = if (birthdayError.value.isNotEmpty()) Red else DarkGray,
                    focusedBorderColor = if (birthdayError.value.isNotEmpty()) Red else Black,
                    unfocusedBorderColor = if (birthdayError.value.isNotEmpty()) Red else Color.Gray,
                    errorBorderColor = Red,
                    errorLabelColor = Red,
                    errorLeadingIconColor = Red//new
                ),
                isError = birthdayError.value.isNotEmpty()
            )
            if (birthdayError.value.isNotEmpty()) {
                Text(birthdayError.value,
                    color = Red,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // username field
            OutlinedTextField(
                value = userName.value,
                onValueChange = {
                    userName.value = it
                    userNameError.value = ""},
                label = {
                    Text(
                        text = "Username",
                        fontFamily = poppinsFontFamily,
                        color = if (userNameError.value.isNotEmpty()) Red else Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person Icon",
                        tint = if (userNameError.value.isNotEmpty()) Red else DarkGray
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = if (userNameError.value.isNotEmpty()) Red else DarkGray,
                    focusedBorderColor = if (userNameError.value.isNotEmpty()) Red else Black,
                    unfocusedBorderColor = if (userNameError.value.isNotEmpty()) Red else Color.Gray,
                    errorBorderColor = Red,
                    errorLabelColor = Red,
                    errorLeadingIconColor = Red
                ),
                isError = userNameError.value.isNotEmpty()
            )

            if (userNameError.value.isNotEmpty()) {
                Text(userNameError.value,
                    color = Red,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                )
            }


            Spacer(modifier = Modifier.height(2.dp))

            // number field
            OutlinedTextField(
                value = contactNumber.value,
                onValueChange = {
                    contactNumber.value = it
                    contactNumberError.value = ""},
                label = { Text(text = "Contact Number",
                    fontFamily = poppinsFontFamily,
                    color = if (contactNumberError.value.isNotEmpty()) Red else Color.Gray) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Icon",
                        tint = if (contactNumberError.value.isNotEmpty()) Red else DarkGray)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = if (contactNumberError.value.isNotEmpty()) Red else DarkGray,
                    focusedBorderColor = if (contactNumberError.value.isNotEmpty()) Red else Black,
                    unfocusedBorderColor = if (contactNumberError.value.isNotEmpty()) Red else Color.Gray,
                    errorBorderColor = Red,
                    errorLabelColor = Red,
                    errorLeadingIconColor = Red
                ),
                isError = contactNumberError.value.isNotEmpty()
            )
            if (contactNumberError.value.isNotEmpty())
                Text(contactNumberError.value,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                ) //NEW

            Spacer(modifier = Modifier.height(2.dp))

            // Email field
            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                    emailError.value = ""},
                label = {
                    Text(text = "Email",
                        fontFamily = poppinsFontFamily,
                        color = if (emailError.value.isNotEmpty()) Red else Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = if (emailError.value.isNotEmpty()) Red else DarkGray
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = if (emailError.value.isNotEmpty()) Red else DarkGray,
                    focusedBorderColor = if (emailError.value.isNotEmpty()) Red else Black,
                    unfocusedBorderColor = if (emailError.value.isNotEmpty()) Red else Color.Gray,
                    errorBorderColor = Red,
                    errorLabelColor = Red,
                    errorLeadingIconColor = Red
                ),
                isError = emailError.value.isNotEmpty()
            )
            if (emailError.value.isNotEmpty())
                Text(emailError.value,
                    color = Red,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                ) //NEW


            Spacer(modifier = Modifier.height(2.dp))

            // Password field
            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                    passwordError.value = ""},
                label = {
                    Text(
                        text = "Password",
                        fontFamily = poppinsFontFamily,
                        color = if (passwordError.value.isNotEmpty()) Red else Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = if (passwordError.value.isNotEmpty()) Red else DarkGray
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            tint = if (passwordError.value.isNotEmpty()) Red else Color.Gray
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = if (passwordError.value.isNotEmpty()) Red else DarkGray,
                    focusedBorderColor = if (passwordError.value.isNotEmpty()) Red else Black,
                    unfocusedBorderColor = if (passwordError.value.isNotEmpty()) Red else Color.Gray,
                    errorBorderColor = Red,
                    errorLabelColor = Red,
                    errorLeadingIconColor = Red
                ),
                isError = passwordError.value.isNotEmpty()
            )
            if (passwordError.value.isNotEmpty())
                Text(passwordError.value,
                    color = Red,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                )

            Spacer(modifier = Modifier.height(2.dp))

            // Confirm Password field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = {
                    Text(
                        text = "Confirm Password",
                        fontFamily = poppinsFontFamily,
                        color = if (confirmPasswordError.isNotEmpty()) Red else Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirm Password Icon",
                        tint = if (confirmPasswordError.isNotEmpty()) Red else DarkGray
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible) "Hide Password" else "Show Password",
                            tint = if (confirmPasswordError.isNotEmpty()) Red else Color.Gray
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = if (confirmPasswordError.isNotEmpty()) Red else DarkGray,
                    focusedBorderColor = if (confirmPasswordError.isNotEmpty()) Red else DarkBlue,
                    unfocusedBorderColor = if (confirmPasswordError.isNotEmpty()) Red else Color.Gray,
                    errorBorderColor = Red,
                    errorLabelColor = Red,
                    errorLeadingIconColor = Red
                )
            )
            if (confirmPasswordError.isNotEmpty())
                Text(confirmPasswordError,
                    color = Red,
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(1.dp))

            TermsAndConditions(isChecked) { newValue -> isChecked = newValue }

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = {
                    if (validateInputs()) {
                        isLoading = true

                        registerController.register(
                            firstName = firstName,
                            lastName = lastName,
                            userName = userName.value,
                            contactNumber = contactNumber.value,
                            email = email.value,
                            password = password.value,
                            confirmPassword = confirmPassword,
                            birthMonth = birthday.value.text.substring(0, 2),
                            birthDay = birthday.value.text.substring(3, 5),
                            birthYear = birthday.value.text.substring(6, 10),
                            onResult = { success, message, errors ->
                                isLoading = false

                                registrationMessage = message
                                registrationSuccessful = success

                                if (success) { showDialog.value = true
                                } else {
                                    val firstError = errors.entries.firstOrNull()
                                    firstError?.let { (field, error) ->
                                        when (field) {
                                            "age" -> showBirthdayError(error)
                                            "username" -> showUsernameError(error)
                                            "mobile_number" -> showContactError(error)
                                            "email" -> showEmailError(error)
                                            "password" -> showPasswordError(error)
                                            else -> showGeneralError(error)
                                        }
                                    }
                                }
                            }
                        )
                    }
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d7151)),
                enabled = !isLoading && firstName.isNotBlank() && lastName.isNotBlank() &&
                        email.value.isNotBlank() && password.value.isNotBlank() && confirmPassword.isNotBlank()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Sign Up", fontFamily = poppinsFontFamily)

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

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Email verification",
                                fontSize = 18.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    },

                    text = { Text("Thank you for signing up! To complete your registration, please check your email for verification",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ) },


                    confirmButton = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ){
                            Button(
                                modifier = Modifier
                                    .height(38.dp)
                                    .width(105.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1d7151),
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    showDialog.value = false
                                    navController.navigate(route = "LoginActivity"){
                                        popUpTo("SignUpActivity") { inclusive = true }
                                    }
                                },
                            ) {
                                Text(
                                    "Got it!",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                TextButton(
                    onClick = { navController.navigate("LoginActivity") },
                    enabled = true
                ) {
                    Text(
                        text = "Log In",
                        color = Color(0xFF4557FF),
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditions(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var hasReachedBottom by remember { mutableStateOf(false) }

    // Track if the user reaches the bottom
    LaunchedEffect(scrollState.value, scrollState.maxValue) {
        if (scrollState.maxValue > 0) { // Ensure maxValue is valid
            hasReachedBottom = scrollState.value >= (scrollState.maxValue - 10) // Small margin
        }
    }

    Surface {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .background(Color.White)
                    .clickable { showDialog = true }
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF1d7151),
                        uncheckedColor = Color.Gray
                    )
                )

                Text(
                    text = "Agree to Terms and Conditions",
                    fontSize = 12.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF4557FF)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand Terms",
                    modifier = Modifier.padding(end = 20.dp)
                )

//                IconButton(onClick = { showDialog = true }) {
//
//                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(vertical = 5.dp),
                        //.padding(top = 70.dp, start = 40.dp, end = 40.dp, bottom = 80.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 70.dp),
                            text = "Terms and Conditions",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(scrollState)
                                .padding(horizontal = 4.dp),
                        ) {
                            Column {
                                Text(
                                    text = "Welcome to Baskit!\nThese Terms and Conditions govern your use\nof our delivery app and services.".trimIndent(),
                                    fontSize = 15.sp,
                                    fontFamily = poppinsFontFamily,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.height(40.dp))

                                Text(
                                    text = "Terms",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = poppinsFontFamily,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                Text(
                                    text = """
                      You must be at least 18 years old to use Baskit and agree to provide accurate personal information when creating an account.
                      You are responsible for maintaining the confidentiality of your login details.
                      Orders placed through the app are subject to availability and acceptance by merchants.
                      Prices displayed on the app include applicable charges unless stated otherwise, and payment must be completed before order confirmation.

                      Delivery times are estimated and may vary due to unforeseen circumstances.
                      Users must provide accurate delivery addresses, and if a recipient is unavailable, the order may be canceled or rescheduled at the user’s cost.
                      Orders can only be canceled before they are accepted by the merchant.
                      Refunds, if applicable, will be processed according to Baskit’s refund policy.

                      Users must not misuse the app, engage in fraud, or harass others.
                      Baskit reserves the right to suspend or terminate accounts that violate these terms.
                      We act as an intermediary between users and merchants and are not responsible for product quality.
                      Additionally, we are not liable for delays or losses due to factors beyond our control.

                      By using Baskit, you agree to our Privacy Policy regarding data collection and usage.
                      We reserve the right to update these terms at any time, and continued use of the app signifies acceptance of any modifications.
                      If you have any questions or concerns, please contact us at Baskit.
                      """.trimIndent(),
                                    fontSize = 15.sp,
                                    fontFamily = poppinsFontFamily,
                                    //textAlign = TextAlign.Justify,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Buttons are ALWAYS visible but DISABLED until scrolled to the end
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                                onClick = {
                                    onCheckedChange(false)
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                enabled = hasReachedBottom
                            ) {
                                Text("Decline")
                            }

                            Button(
                                modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                                onClick = {
                                    onCheckedChange(true)
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d7151)),
                                enabled = hasReachedBottom
                            ) {
                                Text("Accept")
                            }
                        }
                    }
                }
            }
        }
    }
}