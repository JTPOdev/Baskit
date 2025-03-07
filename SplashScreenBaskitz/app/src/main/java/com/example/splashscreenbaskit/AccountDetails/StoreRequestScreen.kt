package com.example.splashscreenbaskit.AccountDetails

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import androidx.navigation.NavController
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.controller.StoreRequestController
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun StoreRequestScreen(navController: NavController) {
    var currentStep by remember { mutableStateOf(1) }

    // Shared state for both steps
    var storeName by remember { mutableStateOf("") }
    var storeAddress by remember { mutableStateOf("") }
    var storeOrigin by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var ownerNumber by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    when (currentStep) {
        1 -> ShopInformationScreen(
            onNext = { name, address, origin, owner, number ->
                storeName = name
                storeAddress = address
                storeOrigin = origin
                ownerName = owner
                ownerNumber = number
                currentStep = 2
            },
            onBack = { navController.popBackStack() }
        )

        2 -> BusinessInformationScreen(
            navController = navController,
            storeName = storeName,
            storeAddress = storeAddress,
            storeOrigin = storeOrigin,
            ownerName = ownerName,
            ownerNumber = ownerNumber,
            onBack = { currentStep = 1 },
            onSendRequest = { registeredStoreName, registeredStoreAddress, storeType, certificate, validId, onSuccess ->
                scope.launch {
                    if (certificate == null || validId == null) {
                        Toast.makeText(context, "Please upload both files!", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    val apiService = RetrofitInstance.create(ApiService::class.java)
                    val controller = StoreRequestController(apiService)

                    val response = controller.sendStoreRequest(
                        userId = "user_id",
                        storeName = storeName,
                        ownerName = ownerName,
                        storePhone = ownerNumber,
                        storeAddress = storeAddress,
                        storeOrigin = storeOrigin,
                        registeredStoreName = registeredStoreName,
                        registeredStoreAddress = registeredStoreAddress,
                        storeStatus = storeType,
                        certificate = certificate,
                        validId = validId
                    )

                    if (response.success) {
                        onSuccess()
                        Toast.makeText(context, "Request Failed: ${response.message}", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate("RequestSentScreen")
                        Toast.makeText(context, "Request Success: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}

// Shop Information Screen (Step 1)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopInformationScreen(
    onNext: (String, String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var storeName by remember { mutableStateOf("") }
    var storeAddress by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var ownerNumber by remember { mutableStateOf("") }

    val options = listOf("Select location --", "Dagupan", "Calasiao")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    val isButtonEnabled = storeName.isNotBlank() &&
            storeAddress.isNotBlank() &&
            ownerName.isNotBlank() &&
            ownerNumber.isNotBlank() && selectedOption != "Select location --"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "< Back",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            modifier = Modifier
                .padding(start = 30.dp)
                .clickable { onBack() }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1D7151), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                }
                Divider(
                    color = Color(0xFFD9D9D9),
                    modifier = Modifier
                        .width(60.dp)
                        .height(2.dp)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFA2A2A2), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily)
                }
            }
            Spacer(modifier = Modifier.height(65.dp))

            Text(
                "Shop Information",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Store Name",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black)
                OutlinedTextField(
                    value = storeName,
                    onValueChange = { storeName = it },
                    placeholder = {
                        Text("Enter your store name",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFF8C8C8C)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Store Address",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black)
                OutlinedTextField(
                    value = storeAddress,
                    onValueChange = { storeAddress = it },
                    placeholder = {
                        Text("Enter your store address",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFF8C8C8C)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Store Location",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded}
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth() .menuAnchor(),
                        readOnly = true,
                        value = selectedOption,
                        onValueChange = {},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {expanded = false}
                    ) {
                        options.forEach{
                            DropdownMenuItem(
                                text = {Text(text = it,style = TextStyle(fontFamily = poppinsFontFamily))},
                                onClick = {
                                    selectedOption = it
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Owner Name",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                OutlinedTextField(
                    value = ownerName,
                    onValueChange = { ownerName = it },
                    placeholder = {
                        Text("Enter your name",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFF8C8C8C)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Owner Number",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black)
                OutlinedTextField(
                    value = ownerNumber,
                    onValueChange = { ownerNumber = it },
                    placeholder = {
                        Text("Enter your number",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFF8C8C8C)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = { onNext(storeName, storeAddress, selectedOption, ownerName, ownerNumber) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = isButtonEnabled
                ) {
                    Text(text = "Next", color = Color.White)
                }
            }
        }
    }
}


// Business Information Screen (Step 2)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessInformationScreen(
    navController: NavController,
    storeName: String,
    storeAddress: String,
    storeOrigin: String,
    ownerName: String,
    ownerNumber: String,
    onBack: () -> Unit,
    onSendRequest: (String, String, String, MultipartBody.Part?, MultipartBody.Part?, () -> Unit) -> Unit
) {
    var registeredStoreName by remember { mutableStateOf("") }
    var registeredStoreAddress by remember { mutableStateOf("") }
    var selectedStoreType by remember { mutableStateOf("Standard") }
    var certificateUri by remember { mutableStateOf<Uri?>(null) }
    var validIdUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val certificateLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        certificateUri = uri
    }

    val validIdLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        validIdUri = uri
    }

    val isButtonEnabled = registeredStoreName.isNotBlank() && registeredStoreAddress.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "< Back",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily,
            modifier = Modifier
                .padding(start = 30.dp)
                .clickable { onBack() }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1D7151), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done",
                        tint = Color.White
                    )
                }
                Divider(
                    color = Color(0xFF1D7151),
                    modifier = Modifier
                        .width(60.dp)
                        .height(2.dp)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1D7151), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                }
            }
            Spacer(modifier = Modifier.height(65.dp))

            Text(
                "Business Information",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Registered shop name",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black)
                OutlinedTextField(
                    value = registeredStoreName,
                    onValueChange = { registeredStoreName = it },
                    placeholder = {
                        Text("Enter your shop name",
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFF8C8C8C)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Registered shop address",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black)
                OutlinedTextField(
                    value = registeredStoreAddress,
                    onValueChange = { registeredStoreAddress = it },
                    placeholder = {
                        Text("Enter your shop address",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFF8C8C8C)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "Certificate of registration",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .background(if (certificateUri != null) Color(0xFFDFF5E6) else Color(0xFFECECEC), RoundedCornerShape(10.dp))
                        .clickable {
                            certificateLauncher.launch("*/*")
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.padding(start = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (certificateUri != null) "Image Selected" else "Upload image",
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = if (certificateUri != null) Color(0xFF1D7151) else Color(0xFF8C8C8C),
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(170.dp))
                        Image(
                            painter = painterResource(id = R.drawable.upload),
                            contentDescription = "Upload Icon",
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "Valid ID",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .background(if (validIdUri != null) Color(0xFFDFF5E6) else Color(0xFFECECEC), RoundedCornerShape(10.dp))
                        .clickable {
                            validIdLauncher.launch("*/*")
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.padding(start = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (validIdUri != null) "Image Selected" else "Upload image",
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = if (validIdUri != null) Color(0xFF1D7151) else Color(0xFF8C8C8C),
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(170.dp))
                        Image(
                            painter = painterResource(id = R.drawable.upload),
                            contentDescription = "Upload Icon",
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(30.dp))

                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = "Choose your store type",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { selectedStoreType = "Standard" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedStoreType == "Standard") Color(0xFFE0F4DE) else Color(0xFFECECEC)
                            ),
                            shape = CircleShape,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Text("Standard",
                                fontSize = 14.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = if (selectedStoreType == "Standard") FontWeight.SemiBold else FontWeight.Normal,
                                color = Color.Black,

                                )
                        }

                        Spacer(modifier = Modifier.width(30.dp))

                        Button(
                            onClick = { selectedStoreType = "Partner" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedStoreType == "Partner") Color(0xFFE0F4DE) else Color(0xFFECECEC)
                            ),
                            shape = CircleShape,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Text("Partnership",
                                fontSize = 14.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = if (selectedStoreType == "Partner") FontWeight.SemiBold else FontWeight.Normal,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                val certificatePart = certificateUri?.let { uriToFilePart(context, "certificate_of_registration", it) }
                                val validIdPart = validIdUri?.let { uriToFilePart(context, "valid_id", it) }

                                onSendRequest(
                                    registeredStoreName,
                                    registeredStoreAddress,
                                    selectedStoreType,
                                    certificatePart,
                                    validIdPart
                                ) {
                                    navController.navigate("RequestSentScreen") {
                                        popUpTo("StoreRequestScreen") { inclusive = true }
                                    }
                                }
                            }
                        },
                        enabled = certificateUri != null && validIdUri != null
                    ) {
                        Text(
                            text = "Send request",
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


fun uriToFilePart(context: Context, partName: String, uri: Uri): MultipartBody.Part? {
    val contentResolver = context.contentResolver
    val fileName = getFileName(context, uri) ?: return null

    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    if (inputStream == null) return null // Ensure stream is not null before proceeding

    val file = File(context.cacheDir, fileName)
    val outputStream = FileOutputStream(file)

    inputStream.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    val requestBody = file.asRequestBody(contentResolver.getType(uri)?.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, file.name, requestBody)
}

// Helper function to get the file name from URI
fun getFileName(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index != -1) return it.getString(index)
        }
    }
    return null
}