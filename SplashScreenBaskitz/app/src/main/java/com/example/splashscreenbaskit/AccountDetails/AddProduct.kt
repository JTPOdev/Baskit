package com.example.splashscreenbaskit.AccountDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun AddProductPreview() {
    AddProduct(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct(navController: NavController) {
    var product by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedWeight by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
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
                    .padding(top = 70.dp, start = 40.dp)
                    .align(Alignment.TopStart)
                    .size(15.dp)
                    .background(Color(0xAAFFFFFF), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(15.dp)
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.add_image),
                contentDescription = "Add Product Image",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
        }

        // Content Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Name
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Seller Description
            Text(
                text = "Seller Description",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Box(
                modifier = Modifier
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
                        text = "Aling Nena's Store",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "0900-000-0000",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "123 Street, Dagupan City, Pang.",
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }

            // Set Price
            Text(
                text = "Set Price",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold
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
                    .height(50.dp),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Weight Options
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val weightOptions = listOf("1 pc", "1/4 kg", "1/2 kg", "1 kg")
                weightOptions.forEach { option ->
                    Button(
                        modifier = Modifier
                            .height(48.dp)
                            .width(80.dp),
                        onClick = { selectedWeight = option },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedWeight == option) Color(0xFF5CC163) else Color(0xFFEEEDED),
                            contentColor = if (selectedWeight == option) Color.White else Color(0xFF747474)
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

            // Select Category
            Text(
                text = "Select Category",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp)) // Added spacing for consistency

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categories = listOf("Fruits", "Vegetables", "Fish", "Meats", "Frozen Foods", "Spices")
                var selectedCategory by remember { mutableStateOf<String?>(null) }

                categories.forEach { category ->
                    Button(
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f),
                        onClick = { selectedCategory = category },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == category) Color(0xFF5CC163) else Color(0xFFEEEDED),
                            contentColor = if (selectedCategory == category) Color.White else Color(0xFF747474)
                        )
                    ) {
                        Text(
                            text = category,
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.SemiBold
                        )
                    }
                }
            }

            // Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .width(147.dp),
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCB3B3B)),
                    enabled = true
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
                        .width(147.dp),
                    onClick = { /* TODO: Implement add action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
                    enabled = true
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