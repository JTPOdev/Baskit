package com.example.splashscreenbaskit.AccountDetails

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.api.ApiService
import com.example.splashscreenbaskit.api.TokenManager
import com.example.splashscreenbaskit.controller.UserStoreController
import com.example.splashscreenbaskit.controllers.ProductController
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun AddProductTestPreview() {
    AddProductTest(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductTest(navController: NavController) {
    var product by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedWeights by remember { mutableStateOf(mutableSetOf<String>()) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var storeName by remember { mutableStateOf("Loading...") }
    var storeNumber by remember { mutableStateOf("Loading...") }
    var storeAddress by remember { mutableStateOf("Loading...") }
    var dada by remember { mutableStateOf("Loading...") }

    val context = LocalContext.current
    val apiService = remember { RetrofitInstance.create(ApiService::class.java) }
    val productController = ProductController(LocalLifecycleOwner.current, context)
    val userStoreController = UserStoreController(LocalLifecycleOwner.current, context, apiService)
    val token = TokenManager.getToken()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            selectedImage = if (Build.VERSION.SDK_INT < 28) {
                @Suppress("DEPRECATION")
                android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    LaunchedEffect(true) {
        userStoreController.fetchStoreDetails { success, errorMessage, store ->
            if (success && store != null) {
                storeName = store.store_name
                storeNumber = store.store_phone_number
                storeAddress = store.store_address


            } else {
                storeName = errorMessage ?: "Error loading store"
                storeName = errorMessage ?: "Error loading store"
                storeNumber = errorMessage ?: "Error loading store"
                storeAddress = errorMessage ?: "Error loading store"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(315.dp)
                .background(Color(0xFFDBDBDB)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(top = 60.dp, start = 25.dp)
                    .align(Alignment.TopStart)
                    .size(35.dp)
                    .zIndex(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(315.dp)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImage != null) {
                    Image(
                        bitmap = selectedImage!!.asImageBitmap(),
                        contentDescription = "Selected Product Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(320.dp)
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.add_image),
                        contentDescription = "Add Product Image",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = product,
                onValueChange = { product = it },
                label = { Text(
                    text = "Enter product name",
                    fontFamily = poppinsFontFamily,
                    color = Color(0xFFBDBDBD),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Seller Description",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .padding(top = 7.dp)
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFEEEDED), shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "$storeName",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "$storeNumber",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "$storeAddress",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Set Price",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                placeholder = { Text(
                    text = "₱0.00",
                    fontFamily = poppinsFontFamily,
                    color = Color(0xFFBDBDBD),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ) },
                modifier = Modifier
                    .width(136.dp)
                    .padding(top = 7.dp),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Select Weight",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.wrapContentWidth() .padding(top = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                val weightOptions = listOf("1 pc", "1/4 kg", "1/2 kg", "1 kg")
                weightOptions.forEach { option ->
                    Button(
                        modifier = Modifier.wrapContentWidth() .height(48.dp),
                        onClick = {
                            selectedWeights = selectedWeights.toMutableSet().apply {
                                if (contains(option)) remove(option) else add(option)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedWeights.contains(option)) Color(0xFF5CC163) else Color(0xFFEEEDED),
                            contentColor = if (selectedWeights.contains(option)) Color.White else Color(0xFF747474)
                        )
                    ) {
                        Text(
                            text = option,
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Select Category",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )

            val categories = listOf(
                Pair("Fruits", "FRUITS"),
                Pair("Vegetables", "VEGETABLES"),
                Pair("Fish", "FISH"),
                Pair("Meats", "MEAT"),
                Pair("Frozen Foods", "FROZEN"),
                Pair("Spices", "SPICES")
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(200.dp).padding(top = 7.dp)
            ) {
                items(categories) { category ->
                    val (label, value) = category

                    Button(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        onClick = { selectedCategory = value },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == value) Color(0xFF5CC163) else Color(0xFFEEEDED),
                            contentColor = if (selectedCategory == value) Color.White else Color(0xFF747474)
                        )
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = if (selectedCategory == value) FontWeight.Bold else FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                        .padding(end = 8.dp),
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCB3B3B))
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                }

                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                        .padding(start = 8.dp),
                    onClick = {
                        if (product.isNotEmpty() && price.isNotEmpty() && imageUri != null) {
                            val imagePart = uriToFileParts(context, "product_image", imageUri!!)
                            if (imagePart != null) {
                                productController.addProduct(
                                    productName = product,
                                    productPrice = price,
                                    productCategory = selectedCategory ?: "Fruits",
                                    productImage = imagePart,
                                    token = token,
                                    onSuccess = { navController.popBackStack() },
                                    onError = { println("Error adding product") }
                                )

                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
                    enabled = product.isNotEmpty() && price.isNotEmpty() && selectedImage != null
                ) {
                    Text(
                        text = "Add",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFontFamily
                    )
                }
            }
        }
    }
}

fun uriToFileParts(context: Context, partName: String, uri: Uri): MultipartBody.Part? {
    val contentResolver = context.contentResolver
    val fileName = getFileNames(context, uri) ?: return null

    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    if (inputStream == null) return null

    val file = File(context.cacheDir, fileName)
    val outputStream = FileOutputStream(file)

    inputStream.use { input -> outputStream.use { output -> input.copyTo(output) } }

    val requestBody = file.asRequestBody(contentResolver.getType(uri)?.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, file.name, requestBody)
}

fun getFileNames(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index != -1) return it.getString(index)
        }
    }
    return null
}