//package com.example.splashscreenbaskit.AccountDetails
//
//import android.graphics.Bitmap
//import android.graphics.ImageDecoder
//import android.os.Build
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.splashscreenbaskit.R
//import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
//
//@Preview(showBackground = true, heightDp = 1000)
//@Composable
//fun AddProductPreview() {
//    AddProduct(navController = rememberNavController())
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddProduct(navController: NavController) {
//    var product by remember { mutableStateOf("") }
//    var price by remember { mutableStateOf("") }
//    var selectedWeight by remember { mutableStateOf<String?>(null) }
//    var selectedCategory by remember { mutableStateOf<String?>(null) }
//    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
//    val context = LocalContext.current
//
//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        uri?.let {
//            selectedImage = if (Build.VERSION.SDK_INT < 28) {
//                @Suppress("DEPRECATION")
//                android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, it)
//            } else {
//                val source = ImageDecoder.createSource(context.contentResolver, it)
//                ImageDecoder.decodeBitmap(source)
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .verticalScroll(rememberScrollState())
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(315.dp)
//                .background(Color(0xFFDBDBDB)),
//            contentAlignment = Alignment.Center
//        ) {
//            IconButton(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier
//                    .padding(top = 70.dp, start = 40.dp)
//                    .align(Alignment.TopStart)
//                    .size(24.dp)
//                    .background(Color(0xAAFFFFFF), shape = CircleShape)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.back),
//                    contentDescription = "Back",
//                    tint = Color.Black,
//                    modifier = Modifier.size(15.dp)
//                )
//            }
//
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .clickable { imagePickerLauncher.launch("image/*") },
//                contentAlignment = Alignment.Center
//            ) {
//                if (selectedImage != null) {
//                    Image(
//                        bitmap = selectedImage!!.asImageBitmap(),
//                        contentDescription = "Selected Product Image",
//                        modifier = Modifier
//                            .size(100.dp)
//                            .background(Color.White, shape = RoundedCornerShape(10.dp))
//                    )
//                } else {
//                    Icon(
//                        painter = painterResource(id = R.drawable.add_image),
//                        contentDescription = "Add Product Image",
//                        modifier = Modifier.size(40.dp),
//                        tint = Color.Unspecified
//                    )
//                }
//            }
//        }
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp, vertical = 20.dp),
//            verticalArrangement = Arrangement.spacedBy(20.dp)
//        ) {
//            OutlinedTextField(
//                value = product,
//                onValueChange = { product = it },
//                label = { Text(
//                    text = "Enter product name",
//                    fontFamily = poppinsFontFamily,
//                    color = Color(0xFFBDBDBD),
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Medium
//                ) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp),
//                singleLine = true,
//                shape = RoundedCornerShape(10.dp),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Black,
//                    unfocusedBorderColor = Color.Gray
//                )
//            )
//
//            Text(
//                text = "Seller Description",
//                fontSize = 16.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .background(Color(0xFFEEEDED), shape = RoundedCornerShape(10.dp)),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(start = 15.dp)
//                        .fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(7.dp)
//                ) {
//                    Text(
//                        text = "Aling Nena's Store",
//                        fontSize = 14.sp,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.Black
//                    )
//                    Text(
//                        text = "0900-000-0000",
//                        fontSize = 14.sp,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.Black
//                    )
//                    Text(
//                        text = "123 Street, Dagupan City, Pang.",
//                        fontSize = 14.sp,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.Black
//                    )
//                }
//            }
//
//            Text(
//                text = "Set Price",
//                fontSize = 16.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = price,
//                onValueChange = { price = it },
//                placeholder = { Text(
//                    text = "₱0.00",
//                    fontFamily = poppinsFontFamily,
//                    color = Color(0xFFBDBDBD),
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Medium
//                ) },
//                modifier = Modifier
//                    .width(136.dp)
//                    .height(56.dp),
//                singleLine = true,
//                shape = RoundedCornerShape(10.dp),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Black,
//                    unfocusedBorderColor = Color.Gray
//                )
//            )
//
//            Text(
//                text = "Select Weight",
//                fontSize = 16.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                val weightOptions = listOf("1 pc", "1/4 kg", "1/2 kg", "1 kg")
//                weightOptions.forEach { option ->
//                    Button(
//                        modifier = Modifier
//                            .height(48.dp)
//                            .weight(1f),
//                        onClick = { selectedWeight = option },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = if (selectedWeight == option) Color(0xFF5CC163) else Color(0xFFEEEDED),
//                            contentColor = if (selectedWeight == option) Color.White else Color(0xFF747474)
//                        )
//                    ) {
//                        Text(
//                            text = option,
//                            fontSize = 14.sp,
//                            fontFamily = poppinsFontFamily,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }
//                }
//            }
//
//            Text(
//                text = "Select Category",
//                fontSize = 16.sp,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.height(180.dp)
//            ) {
//                val categories = listOf("Fruits", "Vegetables", "Fish", "Meats", "Frozen Foods", "Spices")
//                items(categories.size) { index ->
//                    val category = categories[index]
//                    Button(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        onClick = { selectedCategory = category },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = if (selectedCategory == category) Color(0xFF5CC163) else Color(0xFFEEEDED),
//                            contentColor = if (selectedCategory == category) Color.White else Color(0xFF747474)
//                        )
//                    ) {
//                        Text(
//                            text = category,
//                            fontSize = 16.sp,
//                            fontFamily = poppinsFontFamily,
//                            fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.SemiBold,
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 20.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Button(
//                    modifier = Modifier
//                        .height(50.dp)
//                        .weight(1f)
//                        .padding(end = 8.dp),
//                    onClick = { navController.popBackStack() },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCB3B3B))
//                ) {
//                    Text(
//                        text = "Cancel",
//                        color = Color.White,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = poppinsFontFamily
//                    )
//                }
//
//                Button(
//                    modifier = Modifier
//                        .height(50.dp)
//                        .weight(1f)
//                        .padding(start = 8.dp),
//                    onClick = {
//                        if (product.isNotEmpty() && price.isNotEmpty() && selectedImage != null) {
//                            val result = Product(
//                                name = product,
//                                imageBitmap = selectedImage,
//                                price = price.toDoubleOrNull() ?: 0.0,
//                                category = selectedCategory ?: ""
//                            )
//                            navController.previousBackStackEntry?.savedStateHandle?.set("newProduct", result)
//                            navController.popBackStack()
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
//                    enabled = product.isNotEmpty() && price.isNotEmpty() && selectedImage != null
//                ) {
//                    Text(
//                        text = "Add",
//                        color = Color.White,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = poppinsFontFamily
//                    )
//                }
//            }
//        }
//    }
//}